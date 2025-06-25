package org.valcir.service.EntitiesCRUD.impl;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.valcir.entity.Movie;
import org.valcir.repository.MovieRepository;
import org.valcir.service.EntitiesCRUD.IMovieService;

import java.util.List;

@ApplicationScoped
public class MovieService extends CRUDDefaults<Movie>
        implements IMovieService{

    @Inject MovieRepository movieRepository;

    @Override
    protected PanacheRepository<Movie> getRepository() {
        return movieRepository;
    }

    @Override
    public List<Movie> findGoldenRaspberryWinners() {
        return movieRepository.find("goldenRaspberryWinner", true).list();
    }
}
