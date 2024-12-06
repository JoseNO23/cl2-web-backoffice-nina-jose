package pe.edu.i202216351.cl2_web_backoffice_nina_jose.dto;

import java.util.Date;

public record FilmDetailDto(
        Integer filmId,
        String title,
        String description,
        Integer releaseYear,
        Integer languageId,
        String languageName,
        Integer rentalDuration,
        Double rentalRate,
        Integer length,
        Double replacementCost,
        String rating,
        String specialFeatures,
        Date lastUpdate) {
}
