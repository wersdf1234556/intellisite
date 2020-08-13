package org.tonzoc.model;

import org.tonzoc.annotation.*;

// 实时油位(剩余量)
@Table("mechanicsOilSurplus")
public class MechanicsOilSurplusModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "machine_key")
    private String machine_key;  // 机械唯一标识码
    @Column(value = "machine_name")
    private String machine_name;  // 机械名称
    @Column(value = "last_report_at")
    private String last_report_at;  // 油位最后上报时间
    @Column(value = "percentage")
    private String percentage;  // 油位百分比
    @Column(value = "volume")
    private String volume;  // 油位值

    public MechanicsOilSurplusModel() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMachine_key() {
        return machine_key;
    }

    public void setMachine_key(String machine_key) {
        this.machine_key = machine_key;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public String getLast_report_at() {
        return last_report_at;
    }

    public void setLast_report_at(String last_report_at) {
        this.last_report_at = last_report_at;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "MechanicsOilSurplusModel{" +
                "guid='" + guid + '\'' +
                ", machine_key='" + machine_key + '\'' +
                ", machine_name='" + machine_name + '\'' +
                ", last_report_at='" + last_report_at + '\'' +
                ", percentage='" + percentage + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }
}
