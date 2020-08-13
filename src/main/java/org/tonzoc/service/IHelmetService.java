package org.tonzoc.service;

import org.tonzoc.model.HelmetModel;
import org.tonzoc.model.util.ResultJson;

public interface IHelmetService extends IBaseService<HelmetModel> {
    ResultJson saveOnly(HelmetModel helmetModel);

    ResultJson updateOnly(HelmetModel helmetModel);
}
