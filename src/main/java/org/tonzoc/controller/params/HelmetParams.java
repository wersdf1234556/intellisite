package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class HelmetParams {
    @Operator(value = "like", field = "sipNumber")
    private String sipNumber;
    @Operator(value = "like", field = "projectId")
    private String projectId;
    @Operator(value = "like", field = "imei")
    private String imei;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSipNumber() {
        return sipNumber;
    }

    public void setSipNumber(String sipNumber) {
        this.sipNumber = sipNumber;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
