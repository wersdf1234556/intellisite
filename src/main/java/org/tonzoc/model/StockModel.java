package org.tonzoc.model;

import org.tonzoc.annotation.*;

import java.math.BigDecimal;

@Table(value = "stocks")
public class StockModel extends BaseModel {
    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "materialId")
    private String materialId;
    @JoinColumn(value = "name", type = MaterialModel.class, leftColumn = "materialId", rightColumn = "guid")
    private String materialName;
    @JoinColumn(value = "projectId", type = MaterialModel.class, leftColumn = "materialId", rightColumn = "guid")
    private String projectId;
    @Column(value = "time")
    private String time;
    @Column(value = "type")
    private Integer type;
    @Column(value = "num")
    private BigDecimal num;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
