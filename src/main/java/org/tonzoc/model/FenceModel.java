package org.tonzoc.model;

import org.tonzoc.annotation.*;

// 围栏表
@Table(value = "fences")
public class FenceModel extends BaseModel {

    @PrimaryKey
//    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "name")
    private String name;
    @Column(value = "sortId")
    private Integer sortId;
    @Column(value = "projectId")
    private String projectId;
    @JoinColumn(value = "name", type = ProjectModel.class, leftColumn = "projectId", rightColumn = "guid")
    private String projectName;
    @Column(value = "isOutside")
    private Integer isOutside;


    public FenceModel() {
    }

    public FenceModel(String guid, String name, Integer sortId) {
        this.guid = guid;
        this.name = name;
        this.sortId = sortId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
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

    public Integer getIsOutside() {
        return isOutside;
    }

    public void setIsOutside(Integer isOutside) {
        this.isOutside = isOutside;
    }
}
