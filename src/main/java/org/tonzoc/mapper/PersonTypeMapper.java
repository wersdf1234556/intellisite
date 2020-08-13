package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.PersonTypeModel;

import java.util.List;

public interface PersonTypeMapper extends BaseMapper<PersonTypeModel> {

    @Select("SELECT COUNT(1) from persons LEFT JOIN personType ON persons.typeId=personType.guid where personType.code=#{code}")
    Integer countByTypeCode(@Param(value = "code") Integer code);

    @Select("SELECT COUNT(1) from persons LEFT JOIN personType ON persons.typeId=personType.guid where personType.code=#{code} and persons.projectId=#{projectId}")
    Integer countByCodeAndProject(@Param(value = "code") Integer code, @Param(value = "projectId") String projectId);

    @Select("SELECT dbo.personType.guid, dbo.personType.name, dbo.personType.code FROM dbo.personType WHERE code IN (0, 3)")
    List<PersonTypeModel> selectLaowuAndLinshi();
}
