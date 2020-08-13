package org.tonzoc.model.support;

public class CountTotalModel {
    private String total;              //总人数
    private String attendanceTotal;   //考勤总人数
    private String governmentTotal;   //机关人员总数
    private String managerTotal;     //项目人员总数
    private String sumGoverAndManager;    //管理人员总数（项目+机关人员）
    private String contractTotal;         //劳务人员总数
    private String governmentAttenCount;  //机关人员考勤人数
    private String managerAttenCount;     //项目人员考勤人数
    private String gmAttenCount;          //管理人员考勤人数
    private String contractAttenCount;  //劳务人员考勤人数
    private String attenPercent;       //总考勤率
    private String contractPercent;       //劳务人员考勤率


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAttendanceTotal() {
        return attendanceTotal;
    }

    public void setAttendanceTotal(String attendanceTotal) {
        this.attendanceTotal = attendanceTotal;
    }

    public String getGovernmentTotal() {
        return governmentTotal;
    }

    public void setGovernmentTotal(String governmentTotal) {
        this.governmentTotal = governmentTotal;
    }

    public String getManagerTotal() {
        return managerTotal;
    }

    public void setManagerTotal(String managerTotal) {
        this.managerTotal = managerTotal;
    }

    public String getGovernmentAttenCount() {
        return governmentAttenCount;
    }

    public void setGovernmentAttenCount(String governmentAttenCount) {
        this.governmentAttenCount = governmentAttenCount;
    }

    public String getManagerAttenCount() {
        return managerAttenCount;
    }

    public void setManagerAttenCount(String managerAttenCount) {
        this.managerAttenCount = managerAttenCount;
    }

    public String getContractAttenCount() {
        return contractAttenCount;
    }

    public void setContractAttenCount(String contractAttenCount) {
        this.contractAttenCount = contractAttenCount;
    }


    public String getSumGoverAndManager() {
        return sumGoverAndManager;
    }

    public void setSumGoverAndManager(String sumGoverAndManager) {
        this.sumGoverAndManager = sumGoverAndManager;
    }

    public String getAttenPercent() {
        return attenPercent;
    }

    public void setAttenPercent(String attenPercent) {
        this.attenPercent = attenPercent;
    }

    public String getContractPercent() {
        return contractPercent;
    }

    public void setContractPercent(String contractPercent) {
        this.contractPercent = contractPercent;
    }

    public String getContractTotal() {
        return contractTotal;
    }

    public void setContractTotal(String contractTotal) {
        this.contractTotal = contractTotal;
    }

    public String getGmAttenCount() {
        return gmAttenCount;
    }

    public void setGmAttenCount(String gmAttenCount) {
        this.gmAttenCount = gmAttenCount;
    }
}
