package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class MechanicsDataRealTimeQueryParams {

    @Operator(value = "eq", field = "guid")
    private String guid;
    @Operator(value = "eq", field = "realTimeId")
    private String realTimeId;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getRealTimeId() {
        return realTimeId;
    }

    public void setRealTimeId(String realTimeId) {
        this.realTimeId = realTimeId;
    }
}
