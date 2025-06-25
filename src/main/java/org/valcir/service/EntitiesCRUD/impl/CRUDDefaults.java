package org.valcir.service.EntitiesCRUD.impl;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.valcir.service.EntitiesCRUD.ICRUDDefaults;

import java.util.List;

public abstract class CRUDDefaults <T>
        implements ICRUDDefaults<T> {

    protected abstract PanacheRepository<T> getRepository();

    @Override
    public List<T> getAll() {
        return getRepository().findAll().list();
    }

    @Override
    public T findById(Long id) {
        return getRepository().findById(id);
    }

    @Override
    public void persist(T t) {
        getRepository().persist(t);
    }

    @Override
    public void delete(Long id) {
        T t = getRepository().findById(id);
        delete(t);
    }

    @Override
    public void delete(T t) {
        getRepository().delete(t);
    }
}
