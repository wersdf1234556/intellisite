package org.tonzoc.model;

import org.tonzoc.annotation.*;

@Table("securityAlert")
public class SecurityAlertModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "projectId")
    private String projectId;
    @Column(value = "date")
    private String date;
    @Column(value = "name")
    private String name;
    @Column(value = "alert")
    private Integer alert;

    @JoinColumn(value = "name", type = ProjectModel.class, leftColumn = "projectId", rightColumn = "guid")
    private String projectName;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAlert() {
        return alert;
    }

    public void setAlert(Integer alert) {
        this.alert = alert;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
