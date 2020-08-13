package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.JoinColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

@Table("makeUpAttendanceApply")
public class MakeUpAttendanceApplyModel extends BaseModel {
    @PrimaryKey
    @Column(value ="guid")
    private String guid;
    @Column(value ="personGuid")  //人员guid
    private String personGuid;
    @Column(value ="status")
    private String status;
    @Column(value ="currentDepartId")
    private String currentDepartId;
    @Column(value ="currentPersonId")
    private String currentPersonId;
    @Column(value ="applyType")
    private String applyType;
    @Column(value ="content")  //内容
    private String content;
    @Column(value ="firstAttendance") //实际第一次打卡时间
    private String firstAttendance;
    @Column(value ="secondAttendance")//实际第二次打卡时间
    private String secondAttendance;
    @Column(value ="firstMakeAttendance") //后补第一次打卡时间
    private String firstMakeAttendance;
    @Column(value ="secondMakeAttendance")//后补第二次打卡时间
    private String secondMakeAttendance;
    @Column(value ="workDate")//后补第二次打卡时间
    private String workDate;
    @Column(value ="openId")  //微信openId
    private String openId;
    @Column(value ="inState")  //早上考勤状态
    private String inState;
    @Column(value ="outState")  //晚上考勤状态
    private String outState;
    private String currentPersonName;
    private String personName;



    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getInState() {
        return inState;
    }

    public void setInState(String inState) {
        this.inState = inState;
    }

    public String getOutState() {
        return outState;
    }

    public void setOutState(String outState) {
        this.outState = outState;
    }

    public String getCurrentPersonName() {
        return currentPersonName;
    }

    public void setCurrentPersonName(String currentPersonName) {
        this.currentPersonName = currentPersonName;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
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

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstAttendance() {
        return firstAttendance;
    }

    public void setFirstAttendance(String firstAttendance) {
        this.firstAttendance = firstAttendance;
    }

    public String getFirstMakeAttendance() {
        return firstMakeAttendance;
    }

    public void setFirstMakeAttendance(String firstMakeAttendance) {
        this.firstMakeAttendance = firstMakeAttendance;
    }

    public String getSecondMakeAttendance() {
        return secondMakeAttendance;
    }

    public void setSecondMakeAttendance(String secondMakeAttendance) {
        this.secondMakeAttendance = secondMakeAttendance;
    }

    public String getSecondAttendance() {
        return secondAttendance;
    }

    public void setSecondAttendance(String secondAttendance) {
        this.secondAttendance = secondAttendance;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}