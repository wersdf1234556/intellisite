package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class CamerasModuleQueryParams {
    @Operator(value = "eq", field = "camerasId")
    private String camerasId;

    @Operator(value = "eq", field = "moduleId")
    private String moduleId;

    public String getCamerasId() {
        return camerasId;
    }

    public void setCamerasId(String camerasId) {
        this.camerasId = camerasId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}
