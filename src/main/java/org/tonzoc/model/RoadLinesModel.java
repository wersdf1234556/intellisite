package org.tonzoc.model;

import org.tonzoc.annotation.*;

@Table("roadLines")
public class RoadLinesModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "originLng")
    private String originLng; // 经度
    @Column(value = "originLat")
    private String originLat; // 纬度
    @Column(value = "baiduLng")
    private String baiduLng;
    @Column(value = "baiduLat")
    private String baiduLat;
    @Column(value = "isParsed")
    private Integer isParsed;
    @Column(value = "section")
    private String section;
    @Column(value = "sortId")
    private Integer sortId;
    @Column(value = "sortId")
    private String projectId;
    @JoinColumn(value = "name", type = ProjectModel.class, leftColumn = "projectId", rightColumn = "guid")
    private String projectName;

    public RoadLinesModel() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getOriginLng() {
        return originLng;
    }

    public void setOriginLng(String originLng) {
        this.originLng = originLng;
    }

    public String getOriginLat() {
        return originLat;
    }

    public void setOriginLat(String originLat) {
        this.originLat = originLat;
    }

    public String getBaiduLng() {
        return baiduLng;
    }

    public void setBaiduLng(String baiduLng) {
        this.baiduLng = baiduLng;
    }

    public String getBaiduLat() {
        return baiduLat;
    }

    public void setBaiduLat(String baiduLat) {
        this.baiduLat = baiduLat;
    }

    public Integer getIsParsed() {
        return isParsed;
    }

    public void setIsParsed(Integer isParsed) {
        this.isParsed = isParsed;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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
}
