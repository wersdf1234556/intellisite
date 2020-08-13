package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.MakeUpAttendanceApplyModel;

import java.util.List;

public interface MakeUpAttendanceApplyMapper extends BaseMapper<MakeUpAttendanceApplyModel> {
    @Select("SELECT m.guid, personGuid, content,m.inState,m.outState,p1.name personName ,p.name currentPersonName,m.openId, currentPersonId, m.status, firstMakeAttendance, firstAttendance, secondAttendance, secondMakeAttendance, m.workDate, p.name FROM makeUpAttendanceApply m LEFT JOIN persons p ON m.currentPersonId = p.guid LEFT JOIN persons p1 ON m.personGuid = p1.guid WHERE personGuid = #{personGuid} AND workDate > CONVERT ( VARCHAR (100), dateadd(DAY ,- 5, GETDATE()), 112 ) and m.status!='unsubmit'")
    List<MakeUpAttendanceApplyModel> select5Day(String personGuid);
    @Select("SELECT m.inState, m.outState, m.content, m.guid, p1.name personName, personGuid, p.name currentPersonName, m.openId, currentPersonId, m.status, firstMakeAttendance, firstAttendance, secondAttendance, secondMakeAttendance, m.workDate, p.name FROM makeUpAttendanceApply m LEFT JOIN persons p ON m.currentPersonId = p.guid LEFT JOIN persons p1 ON m.personGuid = p1.guid LEFT JOIN flowpath ON flowpath.leaveId = m.guid WHERE workDate > CONVERT ( VARCHAR (100), dateadd(DAY ,- 5, GETDATE()), 112 ) AND flowpath.flowId = '1' AND flowpath.personId =#{ personGuid } AND flowpath.isCompleted = 0")
    List<MakeUpAttendanceApplyModel> queryExamineApply5Day(String personGuid);
}
