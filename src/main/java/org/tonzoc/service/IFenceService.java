package org.tonzoc.service;

import org.tonzoc.model.FenceModel;
import org.tonzoc.model.SecurityAlertModel;
import org.tonzoc.model.support.ReturnValueModel;

import java.util.List;

public interface IFenceService extends IBaseService<FenceModel> {

    List<SecurityAlertModel> alert();
}
