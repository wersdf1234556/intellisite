package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.AttendanceDetailModel;
import org.tonzoc.model.support.AttendanceStateModel;

import java.util.List;
import java.util.Map;

public interface AttendanceDetailMapper extends BaseMapper<AttendanceDetailModel> {

    @Select("SELECT guid, personName, inTime, outTime, workDate, idCard, outState, inState,leave, created_at FROM attendanceDetails  WHERE  idCard=#{idCard} and (outState!='0' or inState!='0') and leave not in(1,2)  and workDate>CONVERT( VARCHAR (100), dateadd(DAY ,- 5, GETDATE()), 112 ) AND workDate < CONVERT ( VARCHAR (100), dateadd(DAY ,0, GETDATE()), 112 )" )
    List<AttendanceDetailModel> select5AbsentByIdCard(String idCard);

    @Select("SELECT mainTable.guid,mainTable.personName,mainTable.inTime,mainTable.outTime,mainTable.workDate,mainTable.inAddress,mainTable.outAddress,mainTable.inState,mainTable.outState,mainTable.idCard,mainTable.leave,mainTable.departmentId FROM attendanceDetails mainTable" +
            " where departmentId=#{departmentId} and workDate=#{workDate} and personName=#{personName}")
    AttendanceDetailModel listByCondition(@Param(value = "departmentId") String departmentId, @Param(value = "workDate") String workDate, @Param(value = "personName") String personName);

    @Select("SELECT p.name as personName," +
            "SUM ( CASE WHEN a.leave=0 and a.inState='3' and a.outState!='4' THEN 1 ELSE 0 END ) AS noSignIn," +
            "SUM ( CASE WHEN a.leave=0 and a.outState='4' and a.inState!='3' THEN 1 ELSE 0 END ) AS noSignOut," +
            "SUM ( CASE WHEN a.inState='3' and a.outState='4' and a.leave=0 THEN 1 ELSE 0  END ) AS absent, " +
            "SUM ( CASE WHEN a.leave='1' THEN 1 ELSE 0 END ) AS leave " +
            "from attendanceDetails a LEFT JOIN persons p on a.idCard=p.idCard " +
            "where a.departmentId=#{departmentId} and p.name=#{personName} and a.workDate>=#{startDate} and a.workDate<=#{endDate} " +
            "GROUP BY p.name")
    AttendanceStateModel stateCount(@Param(value = "departmentId") String departmentId, @Param(value = "personName") String personName,
                                    @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);

}
