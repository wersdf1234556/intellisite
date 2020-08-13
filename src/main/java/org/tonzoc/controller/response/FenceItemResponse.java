package org.tonzoc.controller.response;

import org.tonzoc.model.FenceItemModel;

import java.util.List;

public class FenceItemResponse {
    private String fenceGuid;
    private String fenceName;
    private Integer isOutside;
    private List<FenceItemModel> fenceItemModels;

    public FenceItemResponse() {
    }

    public FenceItemResponse(String fenceGuid, String fenceName, Integer isOutside, List<FenceItemModel> fenceItemModels) {
        this.fenceGuid = fenceGuid;
        this.fenceName = fenceName;
        this.isOutside = isOutside;
        this.fenceItemModels = fenceItemModels;
    }

    public String getFenceGuid() {
        return fenceGuid;
    }

    public void setFenceGuid(String fenceGuid) {
        this.fenceGuid = fenceGuid;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }

    public Integer getIsOutside() {
        return isOutside;
    }

    public void setIsOutside(Integer isOutside) {
        this.isOutside = isOutside;
    }

    public List<FenceItemModel> getFenceItemModels() {
        return fenceItemModels;
    }

    public void setFenceItemModels(List<FenceItemModel> fenceItemModels) {
        this.fenceItemModels = fenceItemModels;
    }
}
