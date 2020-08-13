package org.tonzoc.model;

import org.tonzoc.annotation.*;

// 工作纪录
@Table("mechanicsWorkRecords")
public class MechanicsWorkRecordsModel extends BaseModel {

    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "workTimeId")
    private String workTimeId;
    @Column(value = "start_ts")
    private String start_ts;  // 开始时间戳
    @Column(value = "end_ts")
    private String end_ts;  // 结束时间戳
    @Column(value = "state")
    private String state;  // 工作状态

    @JoinColumn(value = "date", type = MechanicsWorkTimeModel.class, leftColumn = "workTimeId", rightColumn = "guid")
    private String date;
    @JoinColumn(value = "machine_key", type = MechanicsWorkTimeModel.class, leftColumn = "workTimeId", rightColumn = "guid")
    private String machine_key;  // 机械唯一标识
    @JoinColumn(value = "timestamp", type = MechanicsWorkTimeModel.class, leftColumn = "workTimeId", rightColumn = "guid")
    private String timestamp;
    @JoinColumn(value = "machine_name", type = MechanicsWorkTimeModel.class, leftColumn = "workTimeId", rightColumn = "guid")
    private String machine_name;  // 机械名称
    @JoinColumn(value = "work_time", type = MechanicsWorkTimeModel.class, leftColumn = "workTimeId", rightColumn = "guid")
    private String work_time;  // 工作秒数
    @JoinColumn(value = "idle_time", type = MechanicsWorkTimeModel.class, leftColumn = "workTimeId", rightColumn = "guid")
    private String idle_ime;  // 怠速时间
    @JoinColumn(value = "off_time", type = MechanicsWorkTimeModel.class, leftColumn = "workTimeId", rightColumn = "guid")
    private String off_ime;  // 停止时间

    public MechanicsWorkRecordsModel() {
    }

    public MechanicsWorkRecordsModel(String guid, String workTimeId, String start_ts, String end_ts, String state, String date, String machine_key, String timestamp, String machine_name, String work_time, String idle_ime, String off_ime) {
        this.guid = guid;
        this.workTimeId = workTimeId;
        this.start_ts = start_ts;
        this.end_ts = end_ts;
        this.state = state;
        this.date = date;
        this.machine_key = machine_key;
        this.timestamp = timestamp;
        this.machine_name = machine_name;
        this.work_time = work_time;
        this.idle_ime = idle_ime;
        this.off_ime = off_ime;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getWorkTimeId() {
        return workTimeId;
    }

    public void setWorkTimeId(String workTimeId) {
        this.workTimeId = workTimeId;
    }

    public String getStart_ts() {
        return start_ts;
    }

    public void setStart_ts(String start_ts) {
        this.start_ts = start_ts;
    }

    public String getEnd_ts() {
        return end_ts;
    }

    public void setEnd_ts(String end_ts) {
        this.end_ts = end_ts;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getIdle_ime() {
        return idle_ime;
    }

    public void setIdle_ime(String idle_ime) {
        this.idle_ime = idle_ime;
    }

    public String getOff_ime() {
        return off_ime;
    }

    public void setOff_ime(String off_ime) {
        this.off_ime = off_ime;
    }

    @Override
    public String toString() {
        return "MechanicsWorkRecordsModel{" +
                "guid='" + guid + '\'' +
                ", workTimeId='" + workTimeId + '\'' +
                ", start_ts='" + start_ts + '\'' +
                ", end_ts='" + end_ts + '\'' +
                ", state='" + state + '\'' +
                ", date='" + date + '\'' +
                ", machine_key='" + machine_key + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", machine_name='" + machine_name + '\'' +
                ", work_time='" + work_time + '\'' +
                ", idle_ime='" + idle_ime + '\'' +
                ", off_ime='" + off_ime + '\'' +
                '}';
    }
}
