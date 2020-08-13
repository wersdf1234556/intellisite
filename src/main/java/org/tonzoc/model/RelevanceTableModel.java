package org.tonzoc.model;

import org.tonzoc.annotation.*;

// 关联表
@Table("relevanceTable")
public class RelevanceTableModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "mainId")
    private String mainId;
    @Column(value = "dependenceId")
    private String dependenceId;
    @Column(value = "mainSign")
    private Integer mainSign;
    @Column(value = "dependenceSign")
    private Integer dependenceSign;


    public RelevanceTableModel() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getDependenceId() {
        return dependenceId;
    }

    public void setDependenceId(String dependenceId) {
        this.dependenceId = dependenceId;
    }

    public Integer getMainSign() {
        return mainSign;
    }

    public void setMainSign(Integer mainSign) {
        this.mainSign = mainSign;
    }

    public Integer getDependenceSign() {
        return dependenceSign;
    }

    public void setDependenceSign(Integer dependenceSign) {
        this.dependenceSign = dependenceSign;
    }
}
