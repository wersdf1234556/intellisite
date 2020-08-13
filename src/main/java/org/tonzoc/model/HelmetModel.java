package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.JoinColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

import java.util.List;

@Table(value = "helmet")
public class HelmetModel extends BaseModel{
    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "lng")
    private String lng;
    @Column(value = "lat")
    private String lat;
    @Column(value = "projectId")
    private String projectId;
    @Column(value = "sipNumber")
    private String sipNumber;
    @JoinColumn(value = "name",type = ProjectModel.class,leftColumn = "projectId",rightColumn = "guid")
    private String projectName;
    @Column(value = "imei")
    private String imei;


    private List<HelmetFileModel> helmetFileChilrdenList;

    @Override
    public String toString() {
        return "HelmetModel{" +
                "guid='" + guid + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", projectId='" + projectId + '\'' +
                ", sipNumber=" + sipNumber +
                ", projectName='" + projectName + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }

    public List<HelmetFileModel> getHelmetFileChilrdenList() {
        return helmetFileChilrdenList;
    }

    public void setHelmetFileChilrdenList(List<HelmetFileModel> helmetFileChilrdenList) {
        this.helmetFileChilrdenList = helmetFileChilrdenList;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSipNumber() {
        return sipNumber;
    }

    public void setSipNumber(String sipNumber) {
        this.sipNumber = sipNumber;
    }


}
