package org.valcir.service.EntitiesCRUD.impl;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.valcir.entity.Producer;
import org.valcir.repository.ProducerRepository;
import org.valcir.service.EntitiesCRUD.IProducerService;

import java.util.List;

@ApplicationScoped
public class ProducerService extends CRUDDefaults<Producer>
        implements IProducerService {

    private static final Logger LOG = Logger.getLogger(ProducerService.class);

    @Inject ProducerRepository repository;

    @Override
    protected PanacheRepository<Producer> getRepository() {
        return repository;
    }

    @Override
    public Producer findOrCreateByName(String name) {
        List<Producer> producers = findByName(name);
        ambiguousValidation(name, producers);
        return producers.stream().findFirst()
                .orElseGet(() -> {
                    Producer newProducer = new Producer(name);
                    newProducer.persist();
                    return newProducer;
                });
    }

    private List<Producer> findByName(String name) {
        return repository.find("name", name).list();
    }

    private static void ambiguousValidation(String name, List<Producer> producers) {
        if (producers.size() > 1) {
            LOG.warnf("Ambiguous producer name found: '%s'. Using the first match.", name);
        }
    }
}

