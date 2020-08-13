package org.tonzoc.model;

import org.tonzoc.annotation.*;

// 摄像头
@Table(value = "cameras")
public class CamerasModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "deviceSerial")
    private String deviceSerial;  // 序列号
    @Column(value = "chanelNo")
    private String chanelNo;  // 通道号
    @Column(value = "name")
    private String name; // 名称
    @Column(value = "typeId")
    private String typeId; // 关系表
    @Column(value = "brand")
    private String brand; // 品牌
    @Column(value = "cloudPlatformType")
    private String cloudPlatformType;  // 云平台接入类型
    @Column(value = "camerasFunction")
    private String camerasFunction; // 功能
    @Column(value = "sectionId")
    private String sectionId; // 项目部编号
    @Column(value = "projectId")
    private String projectId; // 项目编号
    @Column(value = "isWarning")
    private String isWarning; // 是否发送预警等消息
    @Column(value = "remark")
    private String remark;  // 备注
    @Column(value = "positionOneId")
    private String positionOneId; // 位置1
    @JoinColumn(value = "name", type = PositionOneModel.class, leftColumn = "positionOneId", rightColumn = "guid")
    private String positionOneName;
    @Column(value = "positionTwoId")
    private String positionTwoId; // 位置2
    @JoinColumn(value = "name", type = PositionTwoModel.class, leftColumn = "positionTwoId", rightColumn = "guid")
    private String positionTwoName;
    @Column(value = "lng") // 精度
    private String lng;
    @Column(value = "lat") // 纬度
    private String lat;
    @Column(value = "sortId")
    private Integer sortId; // 排序
    @JoinColumn(value = "name", type = CameraTypeModel.class, leftColumn = "typeId", rightColumn = "guid")
    private String cameraTypeName;  // 摄像头类型
    @JoinColumn(value = "name", type = SectionModel.class, leftColumn = "sectionId", rightColumn = "guid")
    private String sectionName;  // 项目部名称
    @JoinColumn(value = "name", type = ProjectModel.class, leftColumn = "projectId", rightColumn = "guid")
    private String projectName;  // 项目名称
    @Column(value = "url")
    private String url;
    @Column(value = "secondarySortId")
    private Integer secondarySortId;


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getChanelNo() {
        return chanelNo;
    }

    public void setChanelNo(String chanelNo) {
        this.chanelNo = chanelNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCloudPlatformType() {
        return cloudPlatformType;
    }

    public void setCloudPlatformType(String cloudPlatformType) {
        this.cloudPlatformType = cloudPlatformType;
    }

    public String getCamerasFunction() {
        return camerasFunction;
    }

    public void setCamerasFunction(String camerasFunction) {
        this.camerasFunction = camerasFunction;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getIsWarning() {
        return isWarning;
    }

    public void setIsWarning(String isWarning) {
        this.isWarning = isWarning;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPositionOneId() {
        return positionOneId;
    }

    public void setPositionOneId(String positionOneId) {
        this.positionOneId = positionOneId;
    }

    public String getPositionTwoId() {
        return positionTwoId;
    }

    public void setPositionTwoId(String positionTwoId) {
        this.positionTwoId = positionTwoId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getCameraTypeName() {
        return cameraTypeName;
    }

    public void setCameraTypeName(String cameraTypeName) {
        this.cameraTypeName = cameraTypeName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPositionOneName() {
        return positionOneName;
    }

    public void setPositionOneName(String positionOneName) {
        this.positionOneName = positionOneName;
    }

    public String getPositionTwoName() {
        return positionTwoName;
    }

    public void setPositionTwoName(String positionTwoName) {
        this.positionTwoName = positionTwoName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSecondarySortId() {
        return secondarySortId;
    }

    public void setSecondarySortId(Integer secondarySortId) {
        this.secondarySortId = secondarySortId;
    }
}
