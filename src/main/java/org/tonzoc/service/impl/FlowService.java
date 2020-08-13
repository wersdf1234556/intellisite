package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.model.FlowModel;
import org.tonzoc.service.IFlowService;

@Service(value = "flowService")
public class FlowService extends BaseService<FlowModel> implements IFlowService {
}
