package org.valcir.DTO;

import org.valcir.entity.Producer;

import java.util.List;

public record CsvMovieLineDTO(
        Integer year,
        String title,
        String studio,
        List<Producer> producer,
        String winner) {
}
