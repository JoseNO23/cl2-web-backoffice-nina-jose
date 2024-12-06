package pe.edu.i202216351.cl2_web_backoffice_nina_jose.dto;

public record FilmDto(
        Integer filmId,
        String title,
        String language,
        Integer rentalDuration,
        Double rentalRate) {
}
