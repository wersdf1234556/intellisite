package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class CamerasQueryParams {

    @Operator(value = "eq", field = "typeId")
    private String typeId;
    @Operator(value = "eq", field = "sectionId")
    private String sectionId;
    @Operator(value = "eq", field = "projectId")
    private String projectId;

    @Operator(value = "eq", field = "positionOneId")
    private String positionOneId;
    @Operator(value = "eq", field = "positionTwoId")
    private String positionTwoId;
    @Operator(value = "eq", field = "url")
    private String url;
    @Operator(value = "like", field = "name")
    private String name;

    public CamerasQueryParams() {
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getPositionOneId() {
        return positionOneId;
    }

    public void setPositionOneId(String positionOneId) {
        this.positionOneId = positionOneId;
    }

    public String getPositionTwoId() {
        return positionTwoId;
    }

    public void setPositionTwoId(String positionTwoId) {
        this.positionTwoId = positionTwoId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
