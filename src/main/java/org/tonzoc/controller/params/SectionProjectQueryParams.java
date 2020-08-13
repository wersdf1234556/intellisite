package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class SectionProjectQueryParams {

    @Operator(value = "eq", field = "sectionId")
    private String sectionId;

    @Operator(value = "eq", field = "projectId")
    private String projectId;

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
}
