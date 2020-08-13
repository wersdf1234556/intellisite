package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.tonzoc.model.ImageProgressProjectNameModel;

import java.util.List;
@Component
public interface ImageProgressProjectNameMapper extends BaseMapper<ImageProgressProjectNameModel> {

    @Select("SELECT guid, name, pguid  FROM imageProgressProjectName WHERE pguid=#{guid} and isdelete='0'")
    List<ImageProgressProjectNameModel> listByPguid(String guid);
    @Select("SELECT guid, name, pguid FROM imageProgressProjectName WHERE pguid='0' and isdelete='0'")
    List<ImageProgressProjectNameModel> ListfirstProjectName();
}

