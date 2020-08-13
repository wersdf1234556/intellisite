package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class ProjectQueryParams {

    @Operator(value = "eq", field = "guid")
    private String guid;

    @Operator(value = "eq", field = "project_key")
    private String project_key;
    @Operator(value = "eq", field = "status")
    private String status;
    @Operator(value = "eq", field = "type")
    private String type;
    @Operator(value = "like", field = "name")
    private String name;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getProject_key() {
        return project_key;
    }

    public void setProject_key(String project_key) {
        this.project_key = project_key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
