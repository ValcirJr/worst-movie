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
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class MoviesCSVService implements IMoviesCSVService {

    @Inject IProducerService producerService;
    @Inject IStudioService studioService;
    @Inject IMovieService movieService;

    private static final Logger LOG = Logger.getLogger(MoviesCSVService.class);

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
        return new CsvMovieLineDTO(
                Integer.valueOf(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts.length >= 5 ? parts[4] : null
        );
    }

    private void saveProducers(List<CsvMovieLineDTO> csvMovieLines) {
        var filteredByProducer = csvMovieLines.stream()
                .map(CsvMovieLineDTO::producer)
                .distinct().toList();
        filteredByProducer.forEach(producerService::findOrCreateByName);
    }

    private void saveStudios(List<CsvMovieLineDTO> csvMovieLines) {
        var filteredByStudious = csvMovieLines.stream()
                .map(CsvMovieLineDTO::studio)
                .distinct().toList();
        filteredByStudious.forEach(studioService::findOrCreateByName);
    }

    private void saveMovies(List<CsvMovieLineDTO> csvMovieLines) {
        csvMovieLines.forEach(dto -> {
            Producer producer = producerService.findOrCreateByName(dto.producer());
            Studio studio = studioService.findOrCreateByName(dto.studio());
            movieService.persist(new Movie(
                    dto.title(),
                    dto.year(),
                    studio,
                    producer,
                    dto.winner() != null
            ));
        });
        LOG.infof("Saved %d movies", csvMovieLines.size());
    }
}
