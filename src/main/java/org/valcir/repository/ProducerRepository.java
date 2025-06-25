package org.valcir.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.valcir.entity.Producer;

@ApplicationScoped
public class ProducerRepository implements PanacheRepository<Producer> {
}
