package org.tonzoc.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.support.DepartCountModel;
import org.tonzoc.model.support.GeneralModel;
import org.tonzoc.model.support.PStatusModel;
import org.tonzoc.model.support.TypeModel;

import java.util.List;

@Component
public interface PersonMapper extends BaseMapper<PersonModel> {

    @Select("SELECT COUNT(1) from persons LEFT JOIN personType ON persons.typeId = personType.guid where projectId=#{projectId} and personType.code=#{code}")
    Integer selectByPersonCode(@Param(value = "projectId") String projectId, @Param(value = "code") Integer code);
    @Select("SELECT COUNT(1) from persons LEFT JOIN personType ON persons.typeId = personType.guid where personType.code=#{code}")
    Integer listByCodeAndAll(@Param(value = "code") Integer code);

    @Select("SELECT DISTINCT typeId  from persons")
    List<String> listAllByTypeId();
    @Select("SELECT DISTINCT typeId  from persons where projectId=#{projectId}")
    List<String> listTypeId(@Param(value = "projectId") String projectId);

    //根据项目、部门、人员状态进行分组统计
//    @Select("SELECT d.name as departName,COUNT(0) as total," +
//            "SUM ( CASE ps.code WHEN '1'THEN 1 ELSE 0 END ) AS onGuard," +
//            "SUM ( CASE ps.code WHEN '2'THEN 1 ELSE 0 END ) AS leavePersonal," +
//            "SUM ( CASE ps.code WHEN '3'THEN 1 ELSE 0 END ) AS official " +
//            "from persons p LEFT JOIN personStatus ps ON ps.guid=p.statusId " +
//            "LEFT JOIN project pr on p.projectId=pr.guid " +
//            "LEFT JOIN departments d ON p.departmentId=d.guid " +
//            "where p.companyId=#{companyId} and p.projectId=#{projectId}" +
//            "GROUP BY d.name,pr.name")
//    List<DepartCountModel> countByDepartment(@Param(value = "companyId") String companyId, @Param(value = "projectId") String projectId);

    //根据公司、人员状态进行分组统计
    @Select("SELECT d.name as departName,COUNT(0) as total," +
            "SUM ( CASE ps.code WHEN '1'THEN 1 ELSE 0 END ) AS onGuard," +
            "SUM ( CASE ps.code WHEN '2'THEN 1 ELSE 0 END ) AS leavePersonal," +
            "SUM ( CASE ps.code WHEN '3'THEN 1 ELSE 0 END ) AS official " +
            "from persons p LEFT JOIN personStatus ps ON ps.guid=p.statusId " +
            "LEFT JOIN project pr on p.projectId=pr.guid " +
            "LEFT JOIN departments d ON p.departmentId=d.guid " +
            "where d.name!='-1' " +
            "GROUP BY d.name")
    List<DepartCountModel> countByCompany();
    @Select("SELECT d.name as departName,COUNT(0) as total," +
            "SUM ( CASE ps.code WHEN '1'THEN 1 ELSE 0 END ) AS onGuard," +
            "SUM ( CASE ps.code WHEN '2'THEN 1 ELSE 0 END ) AS leavePersonal," +
            "SUM ( CASE ps.code WHEN '3'THEN 1 ELSE 0 END ) AS official " +
            "from persons p LEFT JOIN personStatus ps ON ps.guid=p.statusId " +
            "LEFT JOIN project pr on p.projectId=pr.guid " +
            "LEFT JOIN departments d ON p.departmentId=d.guid " +
            "where p.projectId=#{projectId} and d.name!='-1' " +
            "GROUP BY d.name")
    List<DepartCountModel> countPersonByProject(@Param(value = "projectId") String projectId);

    @Select("SELECT pr.guid as projectId,pr.name as projectName," +
            "SUM ( CASE ps.code WHEN '1'THEN 1 ELSE 0 END ) AS onGuard," +
            "SUM ( CASE ps.code WHEN '2'THEN 1 ELSE 0 END ) AS leavePersonal," +
            "SUM ( CASE ps.code WHEN '3'THEN 1 ELSE 0 END ) AS official " +
            "from persons p LEFT JOIN personStatus ps ON ps.guid=p.statusId " +
            "LEFT JOIN project pr on p.projectId=pr.guid " +
            "LEFT JOIN departments d ON p.departmentId=d.guid " +
            "where pr.name!='-1'" +
            "GROUP BY pr.guid,pr.name,pr.sortId ORDER BY pr.sortId")
    List<PStatusModel> countAllByProject();

    @Select("SELECT pr.guid as projectId,pr.name as projectName," +
            "SUM ( CASE ps.code WHEN '1'THEN 1 ELSE 0 END ) AS onGuard," +
            "SUM ( CASE ps.code WHEN '2'THEN 1 ELSE 0 END ) AS leavePersonal," +
            "SUM ( CASE ps.code WHEN '3'THEN 1 ELSE 0 END ) AS official " +
            "from persons p LEFT JOIN personStatus ps ON ps.guid=p.statusId " +
            "LEFT JOIN project pr on p.projectId=pr.guid " +
            "LEFT JOIN departments d ON p.departmentId=d.guid " +
            "where p.projectId=#{projectId} and pr.name!='-1'" +
            "GROUP BY pr.guid,pr.name,pr.sortId ORDER BY pr.sortId")
    PStatusModel countByProject(@Param(value = "projectId") String projectId);

    @Select("SELECT DISTINCT(pr.guid) from persons p LEFT JOIN project pr on p.projectId=pr.guid WHERE p.projectId!='-1' and pr.name!='-1'")
    List<String> listProjectId();

    @Select("select '35岁以下' typeName," +
            "sum(case when (datediff(\"YEAR\",persons.birthDate,getdate()))<=35 then 1 else 0 end) typeCount,\n" +
            "cast(cast((sum(case when (datediff(\"YEAR\",persons.birthDate,getdate()))<=35 then 1 else 0 end)/((select count(*) from persons)*1.0)*100) as decimal(9,2)) as varchar) 'percent' \n" +
            "from persons\n" +
            "union\n" +
            "select '36~45岁' typeName," +
            "sum(case when ((datediff(\"YEAR\",persons.birthDate,getdate()))>35 and (datediff(\"YEAR\",persons.birthDate,getdate()))<=45) then 1 else 0 end) typeCount,\n" +
            "cast(cast((sum(case when ((datediff(\"YEAR\",persons.birthDate,getdate()))>35 and (datediff(\"YEAR\",persons.birthDate,getdate()))<=45) then 1 else 0 end)/((select count(*) from persons)*1.0)*100) as decimal(9,2)) as varchar) 'percent' \n" +
            "from persons\n" +
            "union\n" +
            "select '46~55岁' typeName,sum(case when ((datediff(\"YEAR\",persons.birthDate,getdate()))>45 and (datediff(\"YEAR\",persons.birthDate,getdate()))<=55) then 1 else 0 end) typeCount,\n" +
            "cast(cast((sum(case when ((datediff(\"YEAR\",persons.birthDate,getdate()))>45 and (datediff(\"YEAR\",persons.birthDate,getdate()))<=55) then 1 else 0 end)/((select count(*) from persons)*1.0)*100) as decimal(9,2)) as varchar) 'percent' \n" +
            "from persons\n" +
            "union\n" +
            "select '55岁以上' typeName,sum(case when (datediff(\"YEAR\",persons.birthDate,getdate()))>55 then 1 else 0 end) typeCount,\n" +
            "cast(cast((sum(case when (datediff(\"YEAR\",persons.birthDate,getdate()))>55 then 1 else 0 end)/((select count(*) from persons)*1.0)*100) as decimal(9,2)) as varchar) 'percent' \n" +
            "from persons")
    List<GeneralModel> stateAllByAge();

    @Select("select '35岁以下' typeName,\n" +
            "sum(case when (datediff(\"YEAR\",persons.birthDate,getdate()))<=35 then 1 else 0 end) typeCount,\n" +
            "cast(cast((sum(case when (datediff(\"YEAR\",persons.birthDate,getdate()))<=35 then 1 else 0 end)/((select count(*) from persons WHERE projectId=#{projectId})*1.0)*100) as decimal(9,2)) as varchar) 'percent' \n" +
            "from persons WHERE projectId=#{projectId}\n" +
            "union\n" +
            "select '36~45岁' typeName,\n" +
            "sum(case when ((datediff(\"YEAR\",persons.birthDate,getdate()))>35 and (datediff(\"YEAR\",persons.birthDate,getdate()))<=45) then 1 else 0 end) typeCount,\n" +
            "cast(cast((sum(case when ((datediff(\"YEAR\",persons.birthDate,getdate()))>35 and (datediff(\"YEAR\",persons.birthDate,getdate()))<=45) then 1 else 0 end)/((select count(*) from persons WHERE projectId=#{projectId})*1.0)*100) as decimal(9,2)) as varchar) 'percent' \n" +
            "from persons WHERE projectId=#{projectId}\n" +
            "union\n" +
            "select '46~55岁' typeName,sum(case when ((datediff(\"YEAR\",persons.birthDate,getdate()))>45 and (datediff(\"YEAR\",persons.birthDate,getdate()))<=55) then 1 else 0 end) typeCount,\n" +
            "cast(cast((sum(case when ((datediff(\"YEAR\",persons.birthDate,getdate()))>45 and (datediff(\"YEAR\",persons.birthDate,getdate()))<=55) then 1 else 0 end)/((select count(*) from persons WHERE projectId=#{projectId})*1.0)*100) as decimal(9,2)) as varchar) 'percent' \n" +
            "from persons WHERE projectId=#{projectId}\n" +
            "union\n" +
            "select '55岁以上' typeName,sum(case when (datediff(\"YEAR\",persons.birthDate,getdate()))>55 then 1 else 0 end) typeCount,\n" +
            "cast(cast((sum(case when (datediff(\"YEAR\",persons.birthDate,getdate()))>55 then 1 else 0 end)/((select count(*) from persons WHERE projectId=#{projectId})*1.0)*100) as decimal(9,2)) as varchar) 'percent' \n" +
            "from persons WHERE projectId=#{projectId}")
    List<GeneralModel> stateByAgeAndProject(@Param(value = "projectId") String projectId);

    @Select("select education.name typeName,\n" +
            "COUNT(*) typeCount,\n" +
            "cast(cast((COUNT(*)/((select count(*) from persons)*1.0)*100) as decimal(9,2)) as varchar) 'percent'\n" +
            "from education LEFT JOIN persons ON persons.educationId = education.guid \n" +
            "group BY education.name")
    List<GeneralModel> stateAllByEducation();

    @Select("select education.name typeName,\n" +
            "COUNT(*) typeCount,\n" +
            "cast(cast((COUNT(*)/((select count(*) from persons WHERE projectId=#{projectId})*1.0)*100) as decimal(9,2)) as varchar) 'percent'\n" +
            "from education LEFT JOIN persons ON persons.educationId = education.guid \n" +
            "WHERE persons.projectId=#{projectId} " +
            "group BY education.name")
    List<GeneralModel> stateByEducationAndProject(@Param(value = "projectId") String projectId);


    //根据project、年龄统计人数
//    @Select("SELECT typeId,personType.name as typeName,COUNT(1) as typeCount from persons LEFT JOIN personType on persons.typeId = personType.guid where projectId=#{projectId} AND birthDate = #{birhDate} GROUP BY typeId,personType.name")
//    List<TypeModel> selectAgeCount(@Param(value = "projectId") String projectId);

    @Select("SELECT guid, code,idCard ,name FROM persons where idCard=#{idCard}")
    PersonModel selectByIdCard(String idCard);

    @Select("SELECT p.guid,p.name, p.openId, p.typeId FROM persons  p LEFT JOIN personType pt on p.typeId=pt.guid where openId = #{openId}")
    PersonModel getByOpenId(String openId);

    @Select("SELECT persons.guid, persons.typeId,persons.projectId,persons.code, persons.companyId,company.code as companyCode,company.name as companyName, persons.name FROM persons LEFT JOIN company ON persons.companyId=company.guid WHERE persons.guid=#{personGuid}")
    PersonModel getCompany(String personGuid);
}
