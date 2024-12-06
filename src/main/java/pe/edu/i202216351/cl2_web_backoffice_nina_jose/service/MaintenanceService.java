package pe.edu.i202216351.cl2_web_backoffice_nina_jose.service;

import pe.edu.i202216351.cl2_web_backoffice_nina_jose.dto.FilmDetailDto;
import pe.edu.i202216351.cl2_web_backoffice_nina_jose.dto.FilmDto;

import java.util.List;

public interface MaintenanceService {

    List<FilmDto> findAllFilms();
    FilmDetailDto findFilmById(int id);
    Boolean updateFilm(FilmDetailDto filmDetailDto);

}
