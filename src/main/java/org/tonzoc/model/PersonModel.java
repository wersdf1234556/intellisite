package org.tonzoc.model;

import org.tonzoc.annotation.*;

import java.util.List;

@Table(value = "persons")
public class PersonModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    //性别
    @Column(value = "sex")
    private Integer sex;
    //人员编码
    @Column(value = "code")
    private String code;
    //人员公司
    @Column(value = "companyId")
    private String companyId;
    @JoinColumn(value = "name", type = CompanyModel.class, leftColumn = "companyId", rightColumn = "guid")
    private String companyName;
    //名字
    @Column(value = "name")
    private String name;
    //证件号
    @Column(value = "idCard")
    private String idCard;

    //状态
    @Column(value = "statusId")
    private String statusId;
    @JoinColumn(value = "name", type = PersonStatusModel.class, leftColumn = "statusId", rightColumn = "guid")
    private String statusName;
    @Column(value = "entryTime")
    private String entryTime;
    //更新时间
    @Column(value = "dimissionTime")
    private String dimissionTime;
    //学历
    @Column(value = "educationId")
    private String educationId;
    @JoinColumn(value = "name", type = EducationModel.class, leftColumn = "educationId", rightColumn = "guid")
    private String educationName;
    @Column(value = "ratingCertificate")
    private String ratingCertificate;
    @Column(value = "workType")
    private String workType;

    @Column(value = "typeId")
    private String typeId;
    @JoinColumn(value = "name", type = PersonTypeModel.class, leftColumn = "typeId", rightColumn = "guid")
    private String personTypeName;
    @Column(value = "attachmentId")
    private String attachmentId;
    @JoinColumn(value = "url", type = AttachmentModel.class, leftColumn = "attachmentId", rightColumn = "guid")
    private String url;
    @Column(value = "birthDate")
    private String birthDate;
    @Column(value = "projectId")
    private String projectId;
    @JoinColumn(value = "name", type = ProjectModel.class, leftColumn = "projectId", rightColumn = "guid")
    private String projectName;
    @Column(value = "departmentId")
    private String departmentId;
    @JoinColumn(value = "name", type = DepartmentModel.class, leftColumn = "departmentId", rightColumn = "guid")
    private String departmentName;
    @Column(value = "lng")
    private String lng;
    @Column(value = "lat")
    private String lat;
    @Column(value = "gpsDeviceSerial")
    private String gpsDeviceSerial;
    @Column(value = "inFence")
    private Integer inFence;

    @Column(value = "fencesId")
    private String fencesId;
    @JoinColumn(value = "name", type = FenceModel.class, leftColumn = "fencesId", rightColumn = "guid")
    private String fencesName;
    @Column(value = "flag")
    private Integer flag;
    @Column(value = "sortId")
    private Integer sortId;
    @Column(value = "telNumber")
    private String telNumber;
    @Column(value = "openId")
    private String openId;

    private List<SafetyHelmetModel> safetyHelmetModels;

    public List<SafetyHelmetModel> getSafetyHelmetModels() {
        return safetyHelmetModels;
    }

    public void setSafetyHelmetModels(List<SafetyHelmetModel> safetyHelmetModels) {
        this.safetyHelmetModels = safetyHelmetModels;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getDimissionTime() {
        return dimissionTime;
    }

    public void setDimissionTime(String dimissionTime) {
        this.dimissionTime = dimissionTime;
    }


    public String getRatingCertificate() {
        return ratingCertificate;
    }

    public void setRatingCertificate(String ratingCertificate) {
        this.ratingCertificate = ratingCertificate;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getGpsDeviceSerial() {
        return gpsDeviceSerial;
    }

    public void setGpsDeviceSerial(String gpsDeviceSerial) {
        this.gpsDeviceSerial = gpsDeviceSerial;
    }

    public Integer getInFence() {
        return inFence;
    }

    public void setInFence(Integer inFence) {
        this.inFence = inFence;
    }

    public String getFencesId() {
        return fencesId;
    }

    public void setFencesId(String fencesId) {
        this.fencesId = fencesId;
    }

    public String getFencesName() {
        return fencesName;
    }

    public void setFencesName(String fencesName) {
        this.fencesName = fencesName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
}