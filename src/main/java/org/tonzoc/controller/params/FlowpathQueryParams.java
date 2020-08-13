package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class FlowpathQueryParams {
    @Operator(value = "eq", field = "leaveId")
    private String leaveId;

    @Operator(value = "eq", field = "personId")
    private String personId;

    @Operator(value = "eq", field = "isBackward")
    private String isBackward;

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getIsBackward() {
        return isBackward;
    }

    public void setIsBackward(String isBackward) {
        this.isBackward = isBackward;
    }
}
