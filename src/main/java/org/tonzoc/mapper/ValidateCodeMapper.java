package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.ValidateCodeModel;

public interface ValidateCodeMapper extends BaseMapper<ValidateCodeModel> {
    @Select("SELECT top 1 code, telNumber, validTime FROM validateCode  where telNumber=#{telNumber} and code=#{validateCode}  order   by  validTime desc")
    ValidateCodeModel select(String telNumber, String validateCode);
}
