package org.tonzoc.model;

public class RequestModel {

    private String app_key;
    private String app_secret;
    private String tenant_key;

    public RequestModel() {
    }

    public RequestModel(String app_key, String app_secret, String tenant_key) {
        this.app_key = app_key;
        this.app_secret = app_secret;
        this.tenant_key = tenant_key;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }

    public String getTenant_key() {
        return tenant_key;
    }

    public void setTenant_key(String tenant_key) {
        this.tenant_key = tenant_key;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "app_key='" + app_key + '\'' +
                ", app_secret='" + app_secret + '\'' +
                ", tenant_key='" + tenant_key + '\'' +
                '}';
    }
}

