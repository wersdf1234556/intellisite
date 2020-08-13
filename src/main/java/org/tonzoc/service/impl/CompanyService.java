package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.model.CompanyModel;
import org.tonzoc.service.ICompanyService;


@Service(value = "companyService")
public class CompanyService extends BaseService<CompanyModel> implements ICompanyService {
}
