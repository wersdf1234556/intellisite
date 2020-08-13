package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class MechanicsCommandQueryParams {

    @Operator(value = "eq", field = "mechanicsId")
    private String mechanicsId;

    public String getMechanicsId() {
        return mechanicsId;
    }

    public void setMechanicsId(String mechanicsId) {
        this.mechanicsId = mechanicsId;
    }
}
