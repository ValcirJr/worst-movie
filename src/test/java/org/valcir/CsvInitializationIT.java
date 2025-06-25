package org.valcir;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.valcir.entity.Movie;
import org.valcir.service.EntitiesCRUD.IMovieService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class CsvInitializationIT {

    @Inject
    IMovieService movieService;

    @Test
    void shouldLoadMoviesFromCsvOnStartup() {
        List<Movie> allMovies = movieService.getAll();
        assertFalse(allMovies.isEmpty(), "Expected movies to be loaded from CSV");
        boolean existsExpectedMovie = allMovies.stream()
                .anyMatch(movie -> movie.getTitle().equalsIgnoreCase("Howard the Duck"));
        assertTrue(existsExpectedMovie, "Expected movie 'Howard the Duck' to be in the database");
    }

}
