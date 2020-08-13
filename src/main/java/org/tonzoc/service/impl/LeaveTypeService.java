package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.model.LeaveTypeModel;
import org.tonzoc.service.ILeaveTypeService;
@Service(value = "leaveTypeService")
public class LeaveTypeService extends BaseService<LeaveTypeModel> implements ILeaveTypeService {
}
