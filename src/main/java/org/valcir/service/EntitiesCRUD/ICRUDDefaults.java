package org.valcir.service.EntitiesCRUD;

import java.util.List;

public interface ICRUDDefaults <T>{

    List<T> getAll();
    T findById(Long id);
    void persist(T t);
    void delete(Long id);
    void delete(T t);
}
