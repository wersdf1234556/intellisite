package org.tonzoc.model;

import org.tonzoc.annotation.*;

import java.math.BigDecimal;

// 油耗
@Table(value = "mechanicsOilConsumption")
public class MOConsumptionModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "machine_key")
    private String machine_key;  // 机械唯一标识
    @Column(value = "machine_name")
    private String machine_name;  // 机械名称
    @Column(value = "date")
    private String date;   // 时间
    @Column(value = "oil")
    private BigDecimal oil;  // 油耗量

    public MOConsumptionModel() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public BigDecimal getOil() {
        return oil;
    }

    public void setOil(BigDecimal oil) {
        this.oil = oil;
    }

    @Override
    public String toString() {
        return "MOConsumptionModel{" +
                "guid='" + guid + '\'' +
                ", machine_key='" + machine_key + '\'' +
                ", machine_name='" + machine_name + '\'' +
                ", date='" + date + '\'' +
                ", oil=" + oil +
                '}';
    }
}
