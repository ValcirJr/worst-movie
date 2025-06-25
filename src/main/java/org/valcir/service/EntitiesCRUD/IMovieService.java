package org.valcir.service.EntitiesCRUD;

import org.valcir.entity.Movie;

import java.util.List;

public interface IMovieService extends ICRUDDefaults<Movie> {
    List<Movie> findGoldenRaspberryWinners();
}
