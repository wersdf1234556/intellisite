package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class AttendanceRecordQueryParams {

    @Operator(value = "eq", field = "attendanceDate")
    private String attendanceDate;

    @Operator(value = "eq", field = "source")
    private String source;
    @Operator(value = "eq", field = "idCard")
    private String idCard;

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
