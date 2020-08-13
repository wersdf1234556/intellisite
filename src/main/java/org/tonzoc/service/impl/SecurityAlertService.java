package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.model.SecurityAlertModel;
import org.tonzoc.service.ISecurityAlertService;

@Service("securityAlertService")
public class SecurityAlertService extends BaseService<SecurityAlertModel> implements ISecurityAlertService {
}
