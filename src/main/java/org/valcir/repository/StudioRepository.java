package org.valcir.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.valcir.entity.Studio;

@ApplicationScoped
public class StudioRepository implements PanacheRepository<Studio> {
}
