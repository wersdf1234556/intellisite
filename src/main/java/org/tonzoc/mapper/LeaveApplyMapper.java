package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.LeaveApplyModel;

import java.util.List;

public interface LeaveApplyMapper extends BaseMapper<LeaveApplyModel> {
    @Select("SELECT l.name leaveTypeName, m.guid, m.personGuid, m.status, m.currentDepartId, m.currentPersonId, m.leaveTypeId, m.content, m.created_at, m.leaveStartTime, m.leaveEndTime, m.openId, p1.name personName FROM leaveApply m LEFT JOIN persons p ON m.currentPersonId = p.guid LEFT JOIN persons p1 ON m.personGuid = p1.guid LEFT JOIN leaveType l ON m.leaveTypeId = l.guid WHERE currentPersonId = #{personGuid} ")
    List<LeaveApplyModel> queryExamineApply5Day(String personGuid);
    @Select("SELECT p.name currentPersonName, p1.name personName, l.name leaveTypeName, m.guid, m.personGuid, m.status, m.currentDepartId, m.currentPersonId, m.leaveTypeId, m.content, m.created_at, m.leaveStartTime, m.leaveEndTime, m.openId FROM leaveApply m LEFT JOIN persons p ON m.currentPersonId = p.guid LEFT JOIN persons p1 ON m.personGuid = p1.guid LEFT JOIN leaveType l ON m.leaveTypeId = l.guid WHERE m.openId = #{openId}  AND m.status != 'unsubmit' and  (leaveStartTime > CONVERT ( VARCHAR (100), dateadd(DAY ,- 5, GETDATE()), 112 ) or  leaveEndTime > CONVERT ( VARCHAR (100), dateadd(DAY ,- 5, GETDATE()), 112 ))")
    List<LeaveApplyModel> leaveApplyList(String openId);
}
