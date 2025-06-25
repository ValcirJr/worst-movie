package org.valcir.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.valcir.entity.Movie;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {
}
