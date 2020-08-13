package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.model.EducationModel;
import org.tonzoc.model.PersonStatusModel;
import org.tonzoc.service.IEducationService;
import org.tonzoc.service.IPersonStatusService;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.ArrayList;
import java.util.List;


@Service(value = "personStatusService")
public class PersonStatusService extends BaseService<PersonStatusModel> implements IPersonStatusService {
    @Override
    public PersonStatusModel selectByCode(Integer code) throws NotOneResultFoundException {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("code", code.toString(), "eq"));

        PersonStatusModel personStatusModel = this.list(sqlQueryParams).get(0);
        return personStatusModel;
    }
}
