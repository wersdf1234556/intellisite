package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class MechanicsWorkRecordsQueryParams {

    @Operator(value = "eq", field = "guid")
    private String guid;
    @Operator(value = "eq", field = "workTimeId")
    private String workTimeId;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getWorkTimeId() {
        return workTimeId;
    }

    public void setWorkTimeId(String workTimeId) {
        this.workTimeId = workTimeId;
    }
}
