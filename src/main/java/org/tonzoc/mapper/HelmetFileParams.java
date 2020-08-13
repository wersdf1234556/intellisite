package org.tonzoc.mapper;

import org.tonzoc.annotation.Operator;

public class HelmetFileParams {
    @Operator(value = "like", field = "sipNumber")
    private String sipNumber;

    public String getSipNumber() {
        return sipNumber;
    }

    public void setSipNumber(String sipNumber) {
        this.sipNumber = sipNumber;
    }
}
