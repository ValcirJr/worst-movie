package org.valcir.DTO;

public record CsvMovieLineDTO(
        Integer year,
        String title,
        String studio,
        String producer,
        String winner) {
}
