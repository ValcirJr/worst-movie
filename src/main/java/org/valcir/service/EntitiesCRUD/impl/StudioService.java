package org.valcir.service.EntitiesCRUD.impl;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import org.valcir.entity.Studio;
import org.valcir.repository.StudioRepository;
import org.valcir.service.EntitiesCRUD.IStudioService;

import java.util.List;

@ApplicationScoped
public class StudioService extends CRUDDefaults<Studio> implements IStudioService {

    private static final Logger LOG = Logger.getLogger(StudioService.class);

    @Inject StudioRepository repository;

    @Override
    protected PanacheRepository<Studio> getRepository() {
        return repository;
    }

    @Override
    public Studio findOrCreateByName(String name) {
        List<Studio> studios = findByName(name);
        ambiguousValidation(name, studios);
        return studios.stream().findFirst()
                .orElseGet(() -> {
                    Studio newStudio = new Studio(name);
                    newStudio.persist();
                    return newStudio;
                });
    }

    private List<Studio> findByName(String name) {
        return repository.find("name", name).list();
    }

    private static void ambiguousValidation(String name, List<Studio> studios) {
        if (studios.size() > 1) {
            LOG.warnf("Ambiguous studio name found: '%s'. Using the first match.", name);
        }
    }
}
