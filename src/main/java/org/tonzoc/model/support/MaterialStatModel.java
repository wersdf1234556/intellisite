package org.tonzoc.model.support;


import org.tonzoc.model.MaterialModel;

import java.util.List;

public class MaterialStatModel {
    private String projectId;
    private String projectName;
    private List<MaterialModel> list;

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

    public List<MaterialModel> getList() {
        return list;
    }

    public void setList(List<MaterialModel> list) {
        this.list = list;
    }

    public MaterialStatModel(String projectId, String projectName, List<MaterialModel> list) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.list = list;
    }
}
