package org.tonzoc.model;

import org.tonzoc.annotation.*;

import java.math.BigDecimal;

// 加油记录表
@Table("mechanicsPlusOil")
public class MechanicsPlusOilModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "machine_key")
    private String machine_key;  // 机械唯一标识
    @Column(value = "machine_name")
    private String machine_name;  // 机械名称
    @Column(value = "refuel_key")
    private String refuel_key;  // 加油唯一标识
    @Column(value = "volume")
    private BigDecimal volume;  // 加油量
    @Column(value = "custom_volume")
    private String custom_volume;  // 手动加油量
    @Column(value = "percentage")
    private String percentage;  // 加油百分比
    @Column(value = "custom_date")
    private String custom_date;   // 手动录入日期
    @Column(value = "start_date_time")
    private String start_date_time;   // 加油开始时间

    public MechanicsPlusOilModel() {
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

    public String getRefuel_key() {
        return refuel_key;
    }

    public void setRefuel_key(String refuel_key) {
        this.refuel_key = refuel_key;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getCustom_volume() {
        return custom_volume;
    }

    public void setCustom_volume(String custom_volume) {
        this.custom_volume = custom_volume;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getCustom_date() {
        return custom_date;
    }

    public void setCustom_date(String custom_date) {
        this.custom_date = custom_date;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    @Override
    public String toString() {
        return "MechanicsPlusOilModel{" +
                "guid='" + guid + '\'' +
                ", machine_key='" + machine_key + '\'' +
                ", machine_name='" + machine_name + '\'' +
                ", refuel_key='" + refuel_key + '\'' +
                ", volume='" + volume + '\'' +
                ", custom_volume='" + custom_volume + '\'' +
                ", percentage='" + percentage + '\'' +
                ", custom_date='" + custom_date + '\'' +
                ", start_date_time='" + start_date_time + '\'' +
                '}';
    }
}
