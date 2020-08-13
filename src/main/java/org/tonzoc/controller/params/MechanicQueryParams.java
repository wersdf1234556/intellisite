package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class MechanicQueryParams {
    @Operator(value = "eq", field = "guid")
    private String guid;

    @Operator(value = "eq", field = "project_key")
    private String project_key;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
