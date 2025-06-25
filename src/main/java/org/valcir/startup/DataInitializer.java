package org.valcir.startup;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.valcir.service.IMoviesCSVService;

@ApplicationScoped
public class DataInitializer {

    @Inject IMoviesCSVService moviesCSVService;

    void onStart(@Observes StartupEvent ev) {
        initialize();
    }

    public void initialize() {
        moviesCSVService.persistFromCSV("/Movielist.csv");
    }


}
