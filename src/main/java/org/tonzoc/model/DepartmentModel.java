package org.tonzoc.model;

import org.tonzoc.annotation.*;

import java.util.List;

@Table(value = "departments")
public class DepartmentModel extends BaseModel {

    @Column(value = "guid")
    @NotInsertColumn
    @PrimaryKey
    private String guid;
    @Column(value = "name")
    private String name;
    @Column(value = "sortId")
    private Integer sortId;
    @Column(value = "parentId")
    private String parentId;
    @JoinColumn(value = "name", type = DepartmentModel.class, leftColumn = "parentId", rightColumn = "guid")
    private String parentDepartName;
    @Column(value = "flag")
    private Integer flag;
    @Column(value = "flowId")
    private String flowId;
    @JoinColumn(value = "name", type = FlowModel.class, leftColumn = "flowId", rightColumn = "guid")
    private String flowName;

    private List<DepartmentModel> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public DepartmentModel() {
    }


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }


    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public List<DepartmentModel> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentModel> children) {
        this.children = children;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getParentDepartName() {
        return parentDepartName;
    }

    public void setParentDepartName(String parentDepartName) {
        this.parentDepartName = parentDepartName;
    }
}
