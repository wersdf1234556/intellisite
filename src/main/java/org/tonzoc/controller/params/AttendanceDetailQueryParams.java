package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class AttendanceDetailQueryParams {

    @Operator(value = "eq", field = "workDate")
    private String workDate;
    @Operator(value = "like", field = "personName")
    private String personName;



    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
