package org.valcir.service.EntitiesCRUD;

import org.valcir.entity.Producer;

public interface IProducerService extends ICRUDDefaults<Producer>{
    Producer findOrCreateByName(String name);
}
