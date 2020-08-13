package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.model.EducationModel;
import org.tonzoc.service.IEducationService;


@Service(value = "educationService")
public class EducationService extends BaseService<EducationModel> implements IEducationService {
}
