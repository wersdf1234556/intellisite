package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.JoinColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

import java.sql.Timestamp;

//流转表
@Table(value = "flowpath")
public class FlowpathModel extends BaseModel {

    @PrimaryKey
//    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "leaveId")
    private String leaveId;
    @Column(value = "personId")
    private String personId;
    @JoinColumn(value = "name", type = PersonModel.class, leftColumn = "personId", rightColumn = "guid")
    private String personName;
    @Column(value = "isCompleted")
    private Integer isCompleted;
    @Column(value = "isBackward")
    private Integer isBackward;
    @Column(value = "flowId")
    private Integer flowId;
    @Column(value = "sortId")
    private Integer sortId;
    @Column(value = "updated_at")
    private Timestamp updated_at;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getIsBackward() {
        return isBackward;
    }

    public void setIsBackward(Integer isBackward) {
        this.isBackward = isBackward;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }


    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }



    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
