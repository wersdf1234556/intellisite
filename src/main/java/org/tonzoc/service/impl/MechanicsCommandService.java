package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.model.MechanicsCommandModel;
import org.tonzoc.service.IMechanicsCommandService;

@Service("mechanicalCommandService")
public class MechanicsCommandService extends BaseService<MechanicsCommandModel> implements IMechanicsCommandService {
}