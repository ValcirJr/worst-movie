package org.valcir.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.valcir.DTO.CsvMovieLineDTO;
import org.valcir.entity.Movie;
import org.valcir.entity.Producer;
import org.valcir.entity.Studio;
import org.valcir.service.EntitiesCRUD.IMovieService;
import org.valcir.service.EntitiesCRUD.IProducerService;
import org.valcir.service.EntitiesCRUD.IStudioService;
import org.valcir.service.IMoviesCSVService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serviço responsável por importar dados de filmes a partir de um arquivo CSV
 * e persistir no banco os produtores, estúdios e filmes.
 */
@ApplicationScoped
public class MoviesCSVService implements IMoviesCSVService {

    @Inject IProducerService producerService;
    @Inject IStudioService studioService;
    @Inject IMovieService movieService;

    private static final Logger LOG = Logger.getLogger(MoviesCSVService.class);

    /**
     * Realiza a leitura e persistência de dados contidos em um arquivo CSV.
     * O CSV deve conter as colunas: year;title;studios;producers;winner
     *
     * @param csvFilePath caminho do arquivo CSV (relativo ao classpath)
     */
    @Override
    @Transactional
    public void persistFromCSV(String csvFilePath) {
        LOG.infof("Persisting CSV file: %s", csvFilePath);
        var lines = readCsvMovieLines(csvFilePath);
        saveProducers(lines);
        saveStudios(lines);
        saveMovies(lines);

        LOG.infof("Persisted CSV file %s", csvFilePath);
    }

    private List<CsvMovieLineDTO> readCsvMovieLines(String csvFilePath) {
        try (BufferedReader reader = createNeweBufferedReader(csvFilePath)) {
            return reader.lines()
                    .skip(1)
                    .filter(line -> line != null && !line.trim().isEmpty())
                    .map(this::parseLine)
                    .toList();
        } catch (Exception e) {
            LOG.error("Failed to read CSV file: " + csvFilePath, e);
            return List.of();
        }
    }

    private BufferedReader createNeweBufferedReader(String csvFilePath) {
        return new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream(csvFilePath)),
                        StandardCharsets.UTF_8)
        );
    }

    private CsvMovieLineDTO parseLine(String line) {
        String[] parts = line.split(";");
        List<Producer> producers = getProducersFromProducerField(parts);
        return new CsvMovieLineDTO(
                Integer.valueOf(parts[0]),
                parts[1],
                parts[2],
                producers,
                parts.length >= 5 ? parts[4] : null
        );
    }

    private static List<Producer> getProducersFromProducerField(String[] parts) {
        return Arrays.stream(
                        parts[3].replace(" and ", ",").split(","))
                .map(String::trim)
                .filter(p -> !p.isEmpty())
                .map(Producer::new)
                .toList();
    }


    private void saveProducers(List<CsvMovieLineDTO> csvMovieLines) {
        var uniqueProducerNames = csvMovieLines.stream()
                .flatMap(dto -> dto.producer().stream())
                .map(Producer::getName)
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .distinct()
                .toList();
        uniqueProducerNames.forEach(producerService::findOrCreateByName);
    }


    private void saveStudios(List<CsvMovieLineDTO> csvMovieLines) {
        var filteredByStudious = csvMovieLines.stream()
                .map(CsvMovieLineDTO::studio)
                .distinct().toList();
        filteredByStudious.forEach(studioService::findOrCreateByName);
    }

    private void saveMovies(List<CsvMovieLineDTO> csvMovieLines) {
        csvMovieLines.forEach(dto -> {
            Set<Producer> producers = dto.producer().stream()
                    .map(p -> producerService.findOrCreateByName(p.getName()))
                    .collect(Collectors.toSet());
            Studio studio = studioService.findOrCreateByName(dto.studio());
            Movie movie = new Movie(
                    dto.title(),
                    dto.year(),
                    studio,
                    producers,
                    dto.winner() != null
            );
            movieService.persist(movie);
        });
        LOG.infof("Saved %d movies", csvMovieLines.size());
    }

}
