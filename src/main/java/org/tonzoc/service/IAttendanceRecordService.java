package org.tonzoc.service;

import org.tonzoc.model.AttendanceRecordModel;
import org.tonzoc.model.support.StatPersonModel;

import java.text.ParseException;

public interface IAttendanceRecordService extends IBaseService<AttendanceRecordModel> {

    StatPersonModel countAtt(String projectId, String date);

    /**保存考勤记录到考勤表
     *
     * @throws ParseException
     */
    void saveRecordToAttendance() throws ParseException;

    /**
     * 保存未考勤人员到考勤表
     */
    void saveNoRecordToAttendance() ;
}
