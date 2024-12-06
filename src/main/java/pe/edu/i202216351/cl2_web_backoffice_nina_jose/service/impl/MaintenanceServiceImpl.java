package pe.edu.i202216351.cl2_web_backoffice_nina_jose.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.dto.FilmDetailDto;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.dto.FilmDto;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.dto.LanguageDto;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.entity.Film;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.entity.Language;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.repository.FilmRepository;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.repository.LanguageRepository;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.service.MaintenanceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public List<FilmDto> findAllFilms() {
        List<FilmDto> films = new ArrayList<>();
        Iterable<Film> iterable = filmRepository.findAll();
        if (!iterable.iterator().hasNext()) {
            throw new RuntimeException("No hay películas disponibles.");
        }
        iterable.forEach(film -> {
            films.add(new FilmDto(
                    film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalDuration(),
                    film.getRentalRate()
            ));
        });
        return films;
    }

    @Override
    public FilmDetailDto findFilmById(int id) {
        return filmRepository.findById(id).map(film -> new FilmDetailDto(
                film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getLanguage().getLanguageId(),
                film.getLanguage().getName(),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getLength(),
                film.getReplacementCost(),
                film.getRating(),
                film.getSpecialFeatures(),
                film.getLastUpdate()
        )).orElseThrow(() -> new RuntimeException("Película no encontrada."));
    }

    @Override
    public Boolean updateFilm(FilmDetailDto filmDetailDto) {
        Optional<Film> optional = filmRepository.findById(filmDetailDto.filmId());
        return optional.map(film -> {
            film.setTitle(filmDetailDto.title());
            film.setDescription(filmDetailDto.description());
            film.setReleaseYear(filmDetailDto.releaseYear());
            film.setRentalDuration(filmDetailDto.rentalDuration());
            film.setRentalRate(filmDetailDto.rentalRate());
            film.setLength(filmDetailDto.length());
            film.setReplacementCost(filmDetailDto.replacementCost());
            film.setRating(filmDetailDto.rating());
            film.setSpecialFeatures(filmDetailDto.specialFeatures());
            film.setLastUpdate(new Date());
            filmRepository.save(film);
            return true;
        }).orElse(false);
    }

    @Override
    public Boolean deleteFilm(int id) {
        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(film -> {
            filmRepository.delete(film);
            return true;
        }).orElse(false);
    }

    @Override
    public Boolean registerFilm(FilmDetailDto filmDetailDto) {
        // Verifica si el idioma existe
        Optional<Language> languageOptional = languageRepository.findById(filmDetailDto.languageId());
        if (languageOptional.isEmpty()) {
            throw new RuntimeException("Idioma no encontrado.");
        }

        // Crea una nueva película
        Film film = new Film();
        film.setTitle(filmDetailDto.title());
        film.setDescription(filmDetailDto.description());
        film.setReleaseYear(filmDetailDto.releaseYear());
        film.setLanguage(languageOptional.get());
        film.setRentalDuration(filmDetailDto.rentalDuration());
        film.setRentalRate(filmDetailDto.rentalRate());
        film.setLength(filmDetailDto.length());
        film.setReplacementCost(filmDetailDto.replacementCost());
        film.setRating(filmDetailDto.rating());
        film.setSpecialFeatures(filmDetailDto.specialFeatures());
        film.setLastUpdate(new Date());

        filmRepository.save(film);
        return true;
    }

    @Override
    public List<LanguageDto> findAllLanguages() {
        Iterable<Language> languages = languageRepository.findAll();
        List<LanguageDto> languageDto = new ArrayList<>();
        for (Language language : languages) {
            languageDto.add(new LanguageDto(language.getLanguageId(), language.getName()));
        }
        return languageDto;
    }
}
