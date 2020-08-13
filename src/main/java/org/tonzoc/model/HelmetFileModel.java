package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.JoinColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

import java.util.Date;

@Table(value = "helmetFile")
public class HelmetFileModel extends BaseModel{
    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "sipNumber")
    private String sipNumber;
    @Column(value = "url")
    private String url;
    @Column(value = "infoId")
    private String infoId;
    @Column(value = "dateTime")
    private Date dateTime;
    @Column(value = "projectId")
    private String projectId;
    @JoinColumn(value = "name",type = ProjectModel.class,leftColumn = "projectId",rightColumn = "guid")
    private String projectName;

    @Override
    public String toString() {
        return "HelmetFileModel{" +
                "guid='" + guid + '\'' +
                ", sipNumber='" + sipNumber + '\'' +
                ", url='" + url + '\'' +
                ", infoId='" + infoId + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSipNumber() {
        return sipNumber;
    }

    public void setSipNumber(String sipNumber) {
        this.sipNumber = sipNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
