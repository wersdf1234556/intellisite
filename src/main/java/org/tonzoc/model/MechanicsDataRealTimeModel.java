package org.tonzoc.model;

import org.tonzoc.annotation.*;

//机械实时数据
@Table("mechanicsDataRealTime")
public class MechanicsDataRealTimeModel extends BaseModel {

    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "state")
    private String state;  // 机械实时状态
    @Column(value = "device_state")
    private String device_state; // 终端状态
    @Column(value = "standing_time")
    private String standing_time; // 当前状态持续时间
    @Column(value = "state_update_at")
    private String state_update_at; // 机械状态上传时间
    @Column(value = "address")
    private String address; // 当前位置
    @Column(value = "longitude")
    private String longitude; // 经度（原始GPS）
    @Column(value = "latitude")
    private String latitude; // 纬度（原始GPS）
    @Column(value = "last_location_at")
    private String last_location_at; // 位置信息上传时间
    @Column(value = "timestamp")
    private String timestamp;
    @Column(value = "realTimeId")
    private String realTimeId;
    @Column(value = "sortId")
    private Integer sortId; // 排序

    // private MechanicsDeviceModel device; // 硬件数据

    @JoinColumn(value = "machine_key", type = MechanicsRealtimeModel.class, leftColumn = "realTimeId", rightColumn = "guid")
    private String machineKey;
    @JoinColumn(value = "machine_name", type = MechanicsRealtimeModel.class, leftColumn = "realTimeId", rightColumn = "guid")
    private String machineName;
    @JoinColumn(value = "online_state", type = MechanicsRealtimeModel.class, leftColumn = "realTimeId", rightColumn = "guid")
    private String onlineState;


    public MechanicsDataRealTimeModel() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDevice_state() {
        return device_state;
    }

    public void setDevice_state(String device_state) {
        this.device_state = device_state;
    }

    public String getStanding_time() {
        return standing_time;
    }

    public void setStanding_time(String standing_time) {
        this.standing_time = standing_time;
    }

    public String getState_update_at() {
        return state_update_at;
    }

    public void setState_update_at(String state_update_at) {
        this.state_update_at = state_update_at;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLast_location_at() {
        return last_location_at;
    }

    public void setLast_location_at(String last_location_at) {
        this.last_location_at = last_location_at;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRealTimeId() {
        return realTimeId;
    }

    public void setRealTimeId(String realTimeId) {
        this.realTimeId = realTimeId;
    }

    public String getMachineKey() {
        return machineKey;
    }

    public void setMachineKey(String machineKey) {
        this.machineKey = machineKey;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        return "MechanicsDataRealTimeModel{" +
                "guid='" + guid + '\'' +
                ", state='" + state + '\'' +
                ", device_state='" + device_state + '\'' +
                ", standing_time='" + standing_time + '\'' +
                ", state_update_at='" + state_update_at + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", last_location_at='" + last_location_at + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", realTimeId='" + realTimeId + '\'' +
                ", sortId='" + sortId + '\'' +
                ", machineKey='" + machineKey + '\'' +
                ", machineName='" + machineName + '\'' +
                ", onlineState='" + onlineState + '\'' +
                '}';
    }
}
