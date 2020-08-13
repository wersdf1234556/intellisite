package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

@Table("leaveApply")
public class LeaveApplyModel extends  BaseModel{
    @PrimaryKey
    @Column(value ="guid")
    private String guid;
    @Column(value ="personGuid")  //人员id
    private String personGuid;
    @Column(value ="status")
    private String status;
    @Column(value ="currentDepartId")
    private String currentDepartId;
    @Column(value ="currentPersonId")
    private String currentPersonId;
    @Column(value ="leaveTypeId")   //请假类型
    private String leaveTypeId;
    @Column(value ="content")  //内容
    private String content;
    @Column(value ="leaveStartTime") //开始时间
    private String leaveStartTime;
    @Column(value ="leaveEndTime") //结束时间
    private String leaveEndTime;
    @Column(value ="openId")  //openId
    private String openId;
    private String personName;
    private String leaveTypeName;
    private String currentPersonName;

    @Override
    public String toString() {
        return "LeaveApplyModel{" +
                "guid='" + guid + '\'' +
                ", personGuid='" + personGuid + '\'' +
                ", status='" + status + '\'' +
                ", currentDepartId='" + currentDepartId + '\'' +
                ", currentPersonId='" + currentPersonId + '\'' +
                ", leaveTypeId='" + leaveTypeId + '\'' +
                ", content='" + content + '\'' +
                ", leaveStartTime=" + leaveStartTime +
                ", leaveEndTime=" + leaveEndTime +
                ", openId='" + openId + '\'' +
                ", personName='" + personName + '\'' +
                ", leaveTypeName='" + leaveTypeName + '\'' +
                ", currentPersonName='" + currentPersonName + '\'' +
                '}';
    }

    public String getCurrentPersonName() {
        return currentPersonName;
    }

    public void setCurrentPersonName(String currentPersonName) {
        this.currentPersonName = currentPersonName;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPersonGuid() {
        return personGuid;
    }

    public void setPersonGuid(String personGuid) {
        this.personGuid = personGuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentDepartId() {
        return currentDepartId;
    }

    public void setCurrentDepartId(String currentDepartId) {
        this.currentDepartId = currentDepartId;
    }

    public String getCurrentPersonId() {
        return currentPersonId;
    }

    public void setCurrentPersonId(String currentPersonId) {
        this.currentPersonId = currentPersonId;
    }

    public String getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(String leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLeaveStartTime() {
        return leaveStartTime;
    }

    public void setLeaveStartTime(String leaveStartTime) {
        this.leaveStartTime = leaveStartTime;
    }

    public String getLeaveEndTime() {
        return leaveEndTime;
    }

    public void setLeaveEndTime(String leaveEndTime) {
        this.leaveEndTime = leaveEndTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}