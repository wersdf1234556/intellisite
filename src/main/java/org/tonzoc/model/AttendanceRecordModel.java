package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

@Table(value = "attendanceRecord")
public class AttendanceRecordModel extends BaseModel {
    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "attendanceTime")
    private String attendanceTime;
    @Column(value = "attendanceDate")
    private String attendanceDate;
    @Column(value = "address")
    private String address;
    @Column(value = "source")
    private Integer source;
    @Column(value = "idCard")
    private String idCard;

    @Override
    public String toString() {
        return "AttendanceRecordModel{" +
                "guid='" + guid + '\'' +
                ", attendanceTime='" + attendanceTime + '\'' +
                ", attendanceDate='" + attendanceDate + '\'' +
                ", address='" + address + '\'' +
                ", source='" + source + '\'' +
                ", idCard='" + idCard + '\'' +
                '}';
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(String attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
