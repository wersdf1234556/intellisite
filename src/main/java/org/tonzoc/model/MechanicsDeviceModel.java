package org.tonzoc.model;

import org.tonzoc.annotation.*;

// 硬件数据
@Table("mechanicsDevice")
public class MechanicsDeviceModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "terminal_sn")
    private String terminal_sn; // 硬件唯一标示
    @Column(value = "model")
    private String model; // 硬件版本
    @Column(value = "battery_percent")
    private String battery_percent; // 当前电量百分比
    @Column(value = "bind_at")
    private String bind_at; // 与机械绑定时间
    @Column(value = "online_at")
    private String online_at; // 入网时间
    @Column(value = "dataRealTimeId")
    private String dataRealTimeId; // 入网时间

    public MechanicsDeviceModel() {
    }

    public MechanicsDeviceModel(String guid, String terminal_sn, String model, String battery_percent, String bind_at, String online_at, String dataRealTimeId) {
        this.guid = guid;
        this.terminal_sn = terminal_sn;
        this.model = model;
        this.battery_percent = battery_percent;
        this.bind_at = bind_at;
        this.online_at = online_at;
        this.dataRealTimeId = dataRealTimeId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTerminal_sn() {
        return terminal_sn;
    }

    public void setTerminal_sn(String terminal_sn) {
        this.terminal_sn = terminal_sn;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBattery_percent() {
        return battery_percent;
    }

    public void setBattery_percent(String battery_percent) {
        this.battery_percent = battery_percent;
    }

    public String getBind_at() {
        return bind_at;
    }

    public void setBind_at(String bind_at) {
        this.bind_at = bind_at;
    }

    public String getOnline_at() {
        return online_at;
    }

    public void setOnline_at(String online_at) {
        this.online_at = online_at;
    }

    public String getDataRealTimeId() {
        return dataRealTimeId;
    }

    public void setDataRealTimeId(String dataRealTimeId) {
        this.dataRealTimeId = dataRealTimeId;
    }

    @Override
    public String toString() {
        return "MechanicsDeviceModel{" +
                "guid='" + guid + '\'' +
                ", terminal_sn='" + terminal_sn + '\'' +
                ", model='" + model + '\'' +
                ", battery_percent='" + battery_percent + '\'' +
                ", bind_at='" + bind_at + '\'' +
                ", online_at='" + online_at + '\'' +
                ", dataRealTimeId='" + dataRealTimeId + '\'' +
                '}';
    }
}
