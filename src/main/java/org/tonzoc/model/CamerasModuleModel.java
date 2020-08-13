package org.tonzoc.model;

import org.tonzoc.annotation.*;

// 摄像头关联模块表
@Table("camerasModule")
public class CamerasModuleModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "camerasId")
    private String camerasId;
    @Column(value = "moduleId")
    private String moduleId;
    @Column(value = "sortId")
    private Integer sortId;
    @Column(value = "secondarySortId")
    private Integer secondarySortId;
    @JoinColumn(value = "name", type = ModuleModel.class, leftColumn = "moduleId", rightColumn = "guid")
    private String moduleName;
    @JoinColumn(value = "deviceSerial", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String deviceSerial;  // 序列号
    @JoinColumn(value = "chanelNo", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String chanelNo;  // 通道号
    @JoinColumn(value = "name", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String cameraName; // 名称
    @JoinColumn(value = "typeId", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String cameraTypeId; // 关系表
    @JoinColumn(value = "brand", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String brand; // 品牌
    @JoinColumn(value = "cloudPlatformType", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String cloudPlatformType;  // 云平台接入类型
    @JoinColumn(value = "camerasFunction", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String camerasFunction; // 功能
    @JoinColumn(value = "sectionId", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String sectionId; // 项目部编号
    @JoinColumn(value = "projectId", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String projectId; // 项目编号
    @JoinColumn(value = "isWarning", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String isWarning; // 是否发送预警等消息
    @JoinColumn(value = "remark", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String remark;  // 备注
    @JoinColumn(value = "positionOneId", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String positionOneId; // 位置1
    @JoinColumn(value = "positionTwoId", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String positionTwoId; // 位置2
    @JoinColumn(value = "lng", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String lng;
    @JoinColumn(value = "lat", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String lat;
    @JoinColumn(value = "url", type = CamerasModel.class, leftColumn = "camerasId", rightColumn = "guid")
    private String url;

    public CamerasModuleModel() {
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCamerasId() {
        return camerasId;
    }

    public void setCamerasId(String camerasId) {
        this.camerasId = camerasId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
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

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getCameraTypeId() {
        return cameraTypeId;
    }

    public void setCameraTypeId(String cameraTypeId) {
        this.cameraTypeId = cameraTypeId;
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
