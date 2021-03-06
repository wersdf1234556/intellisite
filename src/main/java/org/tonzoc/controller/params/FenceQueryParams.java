package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class FenceQueryParams {
    @Operator(value = "eq", field = "guid")
    private String guid;

    @Operator(value = "like", field = "name")
    private String name;
    @Operator(value = "eq", field = "projectId")
    private String projectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
