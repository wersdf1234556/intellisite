package org.tonzoc.model.support;

public class AttendanceStateModel {

    private String personName;
    private Integer noSignIn;
    private Integer noSignOut;
    private Integer absent;
    private Integer leave;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getNoSignIn() {
        return noSignIn;
    }

    public void setNoSignIn(Integer noSignIn) {
        this.noSignIn = noSignIn;
    }

    public Integer getNoSignOut() {
        return noSignOut;
    }

    public void setNoSignOut(Integer noSignOut) {
        this.noSignOut = noSignOut;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }
}
