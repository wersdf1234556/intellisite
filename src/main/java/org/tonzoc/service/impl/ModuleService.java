package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.model.ModuleModel;
import org.tonzoc.service.IModuleService;

@Service(value = "moduleService")
public class ModuleService extends BaseService<ModuleModel> implements IModuleService {
}
