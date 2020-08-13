package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.tonzoc.model.MaterialModel;

import java.math.BigDecimal;
import java.util.List;

@Component
public interface MaterialMapper extends BaseMapper<MaterialModel> {

    @Select("SELECT DISTINCT name  from materials")
    List<String> selectAllName();

    @Select("SELECT mainTable.guid,mainTable.name,mainTable.sortId,mainTable.projectId,mainTable.stockIn,mainTable.stockOut,projectNameTable.name as projectName " +
            "FROM materials mainTable LEFT OUTER JOIN project projectNameTable ON mainTable.projectId = projectNameTable.guid" +
            " where projectId=#{projectId} order by sortId asc")
    List<MaterialModel> listByProjectId(@Param(value = "projectId") String projectId);



}
