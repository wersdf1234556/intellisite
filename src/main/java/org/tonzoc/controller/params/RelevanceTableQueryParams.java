package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class RelevanceTableQueryParams {
    @Operator(value = "eq", field = "guid")
    private String guid;
    @Operator(value = "eq", field = "camerasId")
    private String mainId;

    @Operator(value = "eq", field = "moduleId")
    private String dependenceId;
    @Operator(value = "eq", field = "mainSign")
    private String mainSign;
    @Operator(value = "eq", field = "dependenceSign")
    private String dependenceSign;

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getDependenceId() {
        return dependenceId;
    }

    public void setDependenceId(String dependenceId) {
        this.dependenceId = dependenceId;
    }

    public String getMainSign() {
        return mainSign;
    }

    public void setMainSign(String mainSign) {
        this.mainSign = mainSign;
    }

    public String getDependenceSign() {
        return dependenceSign;
    }

    public void setDependenceSign(String dependenceSign) {
        this.dependenceSign = dependenceSign;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
