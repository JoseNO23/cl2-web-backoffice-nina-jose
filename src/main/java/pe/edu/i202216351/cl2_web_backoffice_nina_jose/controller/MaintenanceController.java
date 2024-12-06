package pe.edu.i202216351.cl2_web_backoffice_nina_jose.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.dto.FilmDetailDto;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.dto.FilmDto;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.service.MaintenanceService;

import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model) {
        try {
            List<FilmDto> films = maintenanceService.findAllFilms();
            model.addAttribute("films", films);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "maintenance";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        if (filmDetailDto == null) {
            model.addAttribute("error", "Película no encontrada.");
            return "error";
        }
        model.addAttribute("film", filmDetailDto);
        return "maintenance_detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance_edit";
    }

    @GetMapping("/edit-confirm")
    public String editConfirm(@ModelAttribute FilmDetailDto filmDetailDto, Model model) {
        maintenanceService.updateFilm(filmDetailDto);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        maintenanceService.deleteFilm(id);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("film", FilmDetailDto.empty());
        model.addAttribute("languages", maintenanceService.findAllLanguages());
        return "maintenance_register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute FilmDetailDto filmDetailDto) {
        maintenanceService.registerFilm(filmDetailDto);
        return "redirect:/maintenance/start";
    }
}
