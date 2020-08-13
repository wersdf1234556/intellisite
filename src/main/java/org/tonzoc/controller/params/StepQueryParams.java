package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class StepQueryParams {
    @Operator(value = "eq", field = "guid")
    private String guid;

}
