package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

import java.util.Arrays;

// 机械时间
@Table("mechanicsWorkTime")
public class MechanicsWorkTimeModel extends BaseModel {

    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "date")
    private String date;
    @Column(value = "machine_key")
    private String machine_key;  // 机械唯一标识
    @Column(value = "timestamp")
    private String timestamp;
    @Column(value = "machine_name")
    private String machine_name;  // 机械名称
    @Column(value = "work_time")
    private String work_time;  // 工作秒数
    @Column(value = "idle_time")
    private String idle_time;  // 怠速时间
    @Column(value = "off_time")
    private String off_time;  // 停止时间
    private MechanicsWorkRecordsModel[] work_records;  // 工作纪录

    public MechanicsWorkTimeModel() {
    }

    public MechanicsWorkTimeModel(String guid, String date, String machine_key, String timestamp, String machine_name, String work_time, String idle_time, String off_time, MechanicsWorkRecordsModel[] work_records) {
        this.guid = guid;
        this.date = date;
        this.machine_key = machine_key;
        this.timestamp = timestamp;
        this.machine_name = machine_name;
        this.work_time = work_time;
        this.idle_time = idle_time;
        this.off_time = off_time;
        this.work_records = work_records;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMachine_key() {
        return machine_key;
    }

    public void setMachine_key(String machine_key) {
        this.machine_key = machine_key;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getIdle_time() {
        return idle_time;
    }

    public void setIdle_time(String idle_time) {
        this.idle_time = idle_time;
    }

    public String getOff_time() {
        return off_time;
    }

    public void setOff_time(String off_time) {
        this.off_time = off_time;
    }

    public MechanicsWorkRecordsModel[] getWork_records() {
        return work_records;
    }

    public void setWork_records(MechanicsWorkRecordsModel[] work_records) {
        this.work_records = work_records;
    }

    @Override
    public String toString() {
        return "MechanicsWorkTimeModel{" +
                "guid='" + guid + '\'' +
                ", date='" + date + '\'' +
                ", machine_key='" + machine_key + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", machine_name='" + machine_name + '\'' +
                ", work_time='" + work_time + '\'' +
                ", idle_time='" + idle_time + '\'' +
                ", off_time='" + off_time + '\'' +
                ", work_records=" + Arrays.toString(work_records) +
                '}';
    }
}
