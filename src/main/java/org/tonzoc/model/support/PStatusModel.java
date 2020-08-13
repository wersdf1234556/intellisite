package org.tonzoc.model.support;

public class PStatusModel {
    private String projectId;
    private String projectName;
    private String onGuard;      //在岗人数
    private String leavePersonal; //事假人数
    private String official; //公出人数

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
