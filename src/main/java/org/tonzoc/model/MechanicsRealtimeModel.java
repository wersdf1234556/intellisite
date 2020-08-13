package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

// 机械实时状态
@Table("mechanicsRealtime")
public class MechanicsRealtimeModel extends BaseModel {

    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "machine_key")
    private String machine_key;  // 机械唯一标识码
    @Column(value = "machine_name")
    private String machine_name; // 机械名称
    @Column(value = "online_state")
    private String online_state; // 机械入网状态
    @Column(value = "sortId")
    private Integer sortId; // 排序
    private MechanicsDataRealTimeModel data_real_time; // 机械实时数据

    public MechanicsRealtimeModel() {
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

    public String getOnline_state() {
        return online_state;
    }

    public void setOnline_state(String online_state) {
        this.online_state = online_state;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public MechanicsDataRealTimeModel getData_real_time() {
        return data_real_time;
    }

    public void setData_real_time(MechanicsDataRealTimeModel data_real_time) {
        this.data_real_time = data_real_time;
    }

    @Override
    public String toString() {
        return "MechanicsRealtimeModel{" +
                "guid='" + guid + '\'' +
                ", machine_key='" + machine_key + '\'' +
                ", machine_name='" + machine_name + '\'' +
                ", online_state='" + online_state + '\'' +
                ", sortId='" + sortId + '\'' +
                ", data_real_time=" + data_real_time +
                '}';
    }
}
