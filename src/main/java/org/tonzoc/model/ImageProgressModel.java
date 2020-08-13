package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.JoinColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

@Table(value = "imageProgress")
public class ImageProgressModel extends BaseModel{
    @PrimaryKey
    @Column(value = "guid")
    private  String guid;
    @Column(value = "imageProgressProjectNameGuid")
    private  String imageProgressProjectNameGuid;
    @Column(value = "designQuantity")
    private  String designQuantity;
    @Column(value = "thisYearPlan")
    private  String thisYearPlan;
    @Column(value = "thisMonthComplete")
    private  String thisMonthComplete;
    @Column(value = "thisYearComplete")
    private  String thisYearComplete;
    @Column(value = "yearCompleteRate")
    private  String yearCompleteRate;
    @Column(value = "cumulativeComplete")
    private  String cumulativeComplete;
    @Column(value = "cumulativeCompleteRate")
    private  String cumulativeCompleteRate;
    @Column(value = "projectGuid")
    private  String projectGuid;
    @Column(value = "isdelete")
    private  String isdelete;
    @Column(value = "createTime")
    private  String createTime;
    @Column(value = "sortId")
    private  String sortId;
    @Column(value = "thisMonthRate")//本月完成比例
    private  String thisMonthRate;
    @JoinColumn(value = "name",type = ImageProgressProjectNameModel.class, leftColumn = "imageProgressProjectNameGuid", rightColumn = "guid")
    private  String imageProgressProjectName;

    @Override
    public String toString() {
        return "ImageProgressModel{" +
                "guid='" + guid + '\'' +
                ", imageProgressProjectNameGuid='" + imageProgressProjectNameGuid + '\'' +
                ", designQuantity='" + designQuantity + '\'' +
                ", thisYearPlan='" + thisYearPlan + '\'' +
                ", thisMonthComplete='" + thisMonthComplete + '\'' +
                ", thisYearComplete='" + thisYearComplete + '\'' +
                ", yearCompleteRate='" + yearCompleteRate + '\'' +
                ", cumulativeComplete='" + cumulativeComplete + '\'' +
                ", cumulativeCompleteRate='" + cumulativeCompleteRate + '\'' +
                ", projectGuid='" + projectGuid + '\'' +
                ", isdelete='" + isdelete + '\'' +
                ", createTime='" + createTime + '\'' +
                ", sortId='" + sortId + '\'' +
                ", thisMonthRate='" + thisMonthRate + '\'' +
                ", imageProgressProjectName='" + imageProgressProjectName + '\'' +
                '}';
    }



    public String getThisMonthRate() {
        return thisMonthRate;
    }

    public void setThisMonthRate(String thisMonthRate) {
        this.thisMonthRate = thisMonthRate;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getImageProgressProjectName() {
        return imageProgressProjectName;
    }

    public void setImageProgressProjectName(String imageProgressProjectName) {
        this.imageProgressProjectName = imageProgressProjectName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getImageProgressProjectNameGuid() {
        return imageProgressProjectNameGuid;
    }

    public void setImageProgressProjectNameGuid(String imageProgressProjectNameGuid) {
        this.imageProgressProjectNameGuid = imageProgressProjectNameGuid;
    }

    public String getDesignQuantity() {
        return designQuantity;
    }

    public void setDesignQuantity(String designQuantity) {
        this.designQuantity = designQuantity;
    }

    public String getThisYearPlan() {
        return thisYearPlan;
    }

    public void setThisYearPlan(String thisYearPlan) {
        this.thisYearPlan = thisYearPlan;
    }

    public String getThisMonthComplete() {
        return thisMonthComplete;
    }

    public void setThisMonthComplete(String thisMonthComplete) {
        this.thisMonthComplete = thisMonthComplete;
    }

    public String getThisYearComplete() {
        return thisYearComplete;
    }

    public void setThisYearComplete(String thisYearComplete) {
        this.thisYearComplete = thisYearComplete;
    }

    public String getYearCompleteRate() {
        return yearCompleteRate;
    }

    public void setYearCompleteRate(String yearCompleteRate) {
        this.yearCompleteRate = yearCompleteRate;
    }

    public String getCumulativeComplete() {
        return cumulativeComplete;
    }

    public void setCumulativeComplete(String cumulativeComplete) {
        this.cumulativeComplete = cumulativeComplete;
    }

    public String getCumulativeCompleteRate() {
        return cumulativeCompleteRate;
    }

    public void setCumulativeCompleteRate(String cumulativeCompleteRate) {
        this.cumulativeCompleteRate = cumulativeCompleteRate;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }
}
