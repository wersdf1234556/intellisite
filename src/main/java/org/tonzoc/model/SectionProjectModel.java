package org.tonzoc.model;

import org.tonzoc.annotation.*;

// 项目部关联项目表
@Table("sectionProject")
public class SectionProjectModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "sectionId")
    private String sectionId;
    @Column(value = "projectId")
    private String projectId;
    @Column(value = "sortId")
    private Integer sortId;
    @JoinColumn(value = "name", type = SectionModel.class, leftColumn = "sectionId", rightColumn = "guid")
    private String sectionName;
    @JoinColumn(value = "name", type = ProjectModel.class, leftColumn = "projectId", rightColumn = "guid")
    private String projectName;

    public SectionProjectModel() {
    }

    public SectionProjectModel(String guid, String sectionId, String projectId, Integer sortId) {
        this.guid = guid;
        this.sectionId = sectionId;
        this.projectId = projectId;
        this.sortId = sortId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
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

    @Override
    public String toString() {
        return "SectionProjectModel{" +
                "guid='" + guid + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", sortId=" + sortId +
                ", sectionName='" + sectionName + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
