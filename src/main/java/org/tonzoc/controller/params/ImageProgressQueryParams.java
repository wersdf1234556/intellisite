package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class ImageProgressQueryParams {
    @Operator(value = "like", field = "name")
    private String name;
    @Operator(value = "eq", field = "projectGuid")
    private String projectGuid;

    public ImageProgressQueryParams() {

    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ImageProgressQueryParams{" +
                "name='" + name + '\'' +
                ", projectGuid='" + projectGuid + '\'' +
                '}';
    }
}
