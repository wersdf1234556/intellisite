package org.tonzoc.model;

import org.tonzoc.annotation.*;

import java.math.BigDecimal;

@Table(value = "materials")
public class MaterialModel extends BaseModel {
    @PrimaryKey
    @NotInsertColumn
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
    @Column(value = "stockIn")
    private BigDecimal stockIn;
    @Column(value = "stockOut")
    private BigDecimal stockOut;

    private BigDecimal surplus;

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

    public BigDecimal getStockIn() {
        return stockIn;
    }

    public void setStockIn(BigDecimal stockIn) {
        this.stockIn = stockIn;
    }

    public BigDecimal getStockOut() {
        return stockOut;
    }

    public void setStockOut(BigDecimal stockOut) {
        this.stockOut = stockOut;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getSurplus() {
        return surplus=stockIn.subtract(stockOut);
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }

}
