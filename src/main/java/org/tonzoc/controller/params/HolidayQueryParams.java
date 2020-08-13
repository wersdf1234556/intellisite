package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class HolidayQueryParams {



    @Operator(value = "eq", field = "date")
    private String date;

    public HolidayQueryParams() {

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
