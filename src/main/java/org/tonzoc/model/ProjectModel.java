package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.NotInsertColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

import java.util.List;

// 项目表
@Table(value = "project")
public class ProjectModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "name")
    private String name;
    @Column(value = "fullName")
    private String fullName;
    @Column(value = "project_key")
    private String project_key;
    @Column(value = "sortId")
    private Integer sortId;
    @Column(value = "lng")
    private String lng;
    @Column(value = "lat")
    private String lat;
    @Column(value = "type")
    private Integer type;   //0:国内1：海外
    @Column(value = "status")
    private Integer status;   //0：在建1：已完成
    @Column(value = "location")
    private String location;
    @Column(value = "contractBalance")
    private String contractBalance;
    @Column(value = "totalPeriod")
    private String totalPeriod;
    @Column(value = "completedPeriod")
    private String completedPeriod;
    @Column(value = "manager")
    private String manager;
    @Column(value = "chiefEngineer")
    private String chiefEngineer;
    @Column(value = "attachmentId")
    private String attachmentId;
    @Column(value = "value")
    private Integer value;
    @Column(value = "scale")
    private Integer scale;

    private List<ImageProgressModel> children;

    private List<HelmetModel> childrenHelmet;


    public ProjectModel() {
    }


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getProject_key() {
        return project_key;
    }

    public void setProject_key(String project_key) {
        this.project_key = project_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
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


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContractBalance() {
        return contractBalance;
    }

    public void setContractBalance(String contractBalance) {
        this.contractBalance = contractBalance;
    }

    public String getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(String totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public String getCompletedPeriod() {
        return completedPeriod;
    }

    public void setCompletedPeriod(String completedPeriod) {
        this.completedPeriod = completedPeriod;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getChiefEngineer() {
        return chiefEngineer;
    }

    public void setChiefEngineer(String chiefEngineer) {
        this.chiefEngineer = chiefEngineer;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public List<ImageProgressModel> getChildren() {
        return children;
    }

    public void setChildren(List<ImageProgressModel> children) {
        this.children = children;
    }

    public List<HelmetModel> getChildrenHelmet() {
        return childrenHelmet;
    }

    public void setChildrenHelmet(List<HelmetModel> childrenHelmet) {
        this.childrenHelmet = childrenHelmet;
    }
}
