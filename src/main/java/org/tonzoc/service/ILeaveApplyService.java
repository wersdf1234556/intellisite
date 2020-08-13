package org.tonzoc.service;

import org.tonzoc.model.LeaveApplyModel;
import org.tonzoc.model.util.ResultJson;

import java.text.ParseException;
import java.util.List;

public interface ILeaveApplyService extends IBaseService<LeaveApplyModel> {
    /**
     * 审批人查看请假请求列表
     * @param openId
     * @return
     */
    ResultJson queryLeaveExamineApply(String openId);

    /**
     * 保存请假成功记录到考勤表
     */
    void saveApplyToAttendance(String guid) throws ParseException;

    /**
     * 用户查看请假记录(从今天往后的)
     * @param openId
     * @return
     */
    List<LeaveApplyModel> leaveApplyList(String openId);
}
