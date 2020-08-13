package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class StockQueryParams {
    @Operator(value = "eq", field = "guid")
    private String guid;

    @Operator(value = "eq", field = "type")
    private String type;
    @Operator(value = "eq", field = "materialId")
    private String materialId;
    @Operator(value = "eq", field = "time")
    private String time;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
