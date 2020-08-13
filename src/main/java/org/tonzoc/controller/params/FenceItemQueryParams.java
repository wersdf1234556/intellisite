package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class FenceItemQueryParams {
    @Operator(value = "eq", field = "guid")
    private String guid;

    @Operator(value = "eq", field = "fenceGuid")
    private String fenceGuid;

    public String getFenceGuid() {
        return fenceGuid;
    }

    public void setFenceGuid(String fenceGuid) {
        this.fenceGuid = fenceGuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
