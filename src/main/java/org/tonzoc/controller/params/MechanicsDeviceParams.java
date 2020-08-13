package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class MechanicsDeviceParams {

    @Operator(value = "eq", field = "guid")
    private String guid;
    @Operator(value = "eq", field = "dataRealTimeId")
    private String dataRealTimeId;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDataRealTimeId() {
        return dataRealTimeId;
    }

    public void setDataRealTimeId(String dataRealTimeId) {
        this.dataRealTimeId = dataRealTimeId;
    }
}
