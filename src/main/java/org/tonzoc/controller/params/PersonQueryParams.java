package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class PersonQueryParams {
    @Operator(value = "eq", field = "guid")
    private String guid;
    @Operator(value = "like", field = "name")
    private String name;
    @Operator(value = "eq", field = "departmentId")
    private String departmentId;
    @Operator(value = "like", field = "code")
    private String code;
    @Operator(value = "eq", field = "typeId")
    private String typeId;
    @Operator(value = "eq", field = "companyId")
    private String companyId;
    @Operator(value = "eq", field = "projectId")
    private String projectId;
    @Operator(value = "eq", field = "educationId")
    private String educationId;
    @Operator(value = "eq", field = "statusId")
    private String statusId;
    @Operator(value = "eq", field = "sex")
    private String sex;
    @Operator(value = "eq", field = "gpsDeviceSerial")
    private String gpsDeviceSerial;
    @Operator(value = "eq", field = "inFence")
    private String inFence;  //1：围栏内人员2：围栏外人员

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGpsDeviceSerial() {
        return gpsDeviceSerial;
    }

    public void setGpsDeviceSerial(String gpsDeviceSerial) {
        this.gpsDeviceSerial = gpsDeviceSerial;
    }

    public String getInFence() {
        return inFence;
    }

    public void setInFence(String inFence) {
        this.inFence = inFence;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
