package org.tonzoc.service;

import org.tonzoc.model.AttendanceDetailModel;
import org.tonzoc.model.MakeUpAttendanceApplyModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.support.param.SqlQueryParam;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;


public interface IAttendanceDetailService extends IBaseService<AttendanceDetailModel> {

    /**
     * 根据openid查询该人员近5天异常考勤,只返回未提交过补考勤申请的考勤记录
     * @param openId
     * @return
     */
    ResultJson queryAbsent(String openId) throws ParseException;

    ResultJson queryApply(String openId);

    void export(HttpServletResponse response, String departmentId, String startDate, String endDate) throws Exception;
}
