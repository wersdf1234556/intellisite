package org.tonzoc.service;

import org.tonzoc.model.MakeUpAttendanceApplyModel;
import org.tonzoc.model.util.ResultJson;

public interface IMakeUpAttendanceApplyService extends IBaseService<MakeUpAttendanceApplyModel> {
    ResultJson queryExamineApply(String openId);
}
