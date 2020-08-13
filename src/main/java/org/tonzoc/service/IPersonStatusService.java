package org.tonzoc.service;


import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.model.PersonStatusModel;

public interface IPersonStatusService extends IBaseService<PersonStatusModel> {
    PersonStatusModel selectByCode(Integer code) throws NotOneResultFoundException;
}
