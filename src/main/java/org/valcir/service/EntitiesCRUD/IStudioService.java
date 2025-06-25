package org.valcir.service.EntitiesCRUD;

import org.valcir.entity.Studio;

public interface IStudioService extends ICRUDDefaults<Studio>{
    Studio findOrCreateByName(String name);
}
