package org.tonzoc.model.support;

//人员、考勤统计
public class StatPersonModel {
    private String name;
    private String total;
    private String attendanceNum;//出勤人数
    private String attendancePercent; //出勤率

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAttendanceNum() {
        return attendanceNum;
    }

    public void setAttendanceNum(String attendanceNum) {
        this.attendanceNum = attendanceNum;
    }

    public String getAttendancePercent() {
        return attendancePercent;
    }

    public void setAttendancePercent(String attendancePercent) {
        this.attendancePercent = attendancePercent;
    }
}
