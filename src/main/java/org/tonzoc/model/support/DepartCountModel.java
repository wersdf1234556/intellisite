package org.tonzoc.model.support;

public class DepartCountModel {
    private String departName;
    private String total;
    private String onGuard;      //在岗人数
    private String leavePersonal; //事假人数
    private String official; //公出人数

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOnGuard() {
        return onGuard;
    }

    public void setOnGuard(String onGuard) {
        this.onGuard = onGuard;
    }

    public String getLeavePersonal() {
        return leavePersonal;
    }

    public void setLeavePersonal(String leavePersonal) {
        this.leavePersonal = leavePersonal;
    }

    public String getOfficial() {
        return official;
    }

    public void setOfficial(String official) {
        this.official = official;
    }
}
