package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.AttendanceRecordModel;

import java.util.List;

public interface AttendanceRecordMapper extends BaseMapper<AttendanceRecordModel> {
    @Select("SELECT COUNT(distinct mainTable.idCard) FROM attendanceRecord mainTable " +
            "WHERE mainTable.idCard IN (SELECT DISTINCT idCard from persons p ) and (mainTable.attendanceDate=#{attendanceDate}) and mainTable.idCard!=''")
    Integer countSignInAll(@Param(value = "attendanceDate") String attendanceDate);

    @Select("SELECT COUNT(distinct mainTable.idCard) FROM attendanceRecord mainTable left join persons p on mainTable.idCard=p.idCard " +
            "WHERE p.projectId=#{projectId} and mainTable.attendanceDate=#{attendanceDate} and mainTable.idCard!=''")
    Integer countSignInByProject(@Param(value = "projectId") String projectId,@Param(value = "attendanceDate") String attendanceDate);

    @Select("SELECT COUNT(DISTINCT mainTable.idCard)  from attendanceRecord mainTable LEFT JOIN persons ON mainTable.idCard  = persons.idCard where persons.typeId =#{typeId} " +
            "and mainTable.attendanceDate=#{attendanceDate}")
    Integer countAttByTypeId(@Param(value = "typeId") String typeId, @Param(value = "attendanceDate") String attendanceDate);

    @Select("SELECT idCard, attendanceDate, STUFF(( SELECT ',' + attendanceTime FROM attendanceRecord WHERE idCard = a.idCard AND attendanceDate = CONVERT (VARCHAR(10), GETDATE(), 120) FOR xml path ('')), 1, 1, '' ) AS attendanceTime, STUFF(( SELECT ',' + address FROM attendanceRecord WHERE idCard = a.idCard AND attendanceDate = CONVERT (VARCHAR(10), GETDATE(), 120) FOR xml path ('')), 1, 1, '' ) AS address FROM attendanceRecord a GROUP BY idCard, attendanceDate WITH ROLLUP HAVING attendanceDate = CONVERT (VARCHAR(10), GETDATE(), 120)  ")
    List<AttendanceRecordModel> listRecord();

}
