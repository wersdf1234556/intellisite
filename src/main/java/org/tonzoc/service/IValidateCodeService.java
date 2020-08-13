package org.tonzoc.service;

import org.tonzoc.model.ValidateCodeModel;
import org.tonzoc.model.util.ResultJson;

public interface IValidateCodeService extends IBaseService<ValidateCodeModel> {
    /**
     * 验证码
     * @param telNumber
     * @param validateCode
     * @return
     */

    ResultJson validateAndSaveOpenid(String telNumber, String validateCode, String openId, String idCard, String typeId, String companyId, String name);
}
