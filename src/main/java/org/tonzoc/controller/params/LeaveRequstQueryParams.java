package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class LeaveRequstQueryParams {
    @Operator(value = "eq", field = "guid")
    private String guid;

    @Operator(value = "eq", field = "personGuid")
    private String personGuid;
    @Operator(value = "eq", field = "submitTime")
    private String submitTime;
    @Operator(value = "eq", field = "leaveType")
    private String leaveType;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPersonGuid() {
        return personGuid;
    }

    public void setPersonGuid(String personGuid) {
        this.personGuid = personGuid;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
}
