package org.tonzoc.controller.params;

import org.tonzoc.annotation.Operator;

public class ImageProgressProjectNameQueryParams {
    @Operator(value = "like", field = "name")
    private String name;

    public ImageProgressProjectNameQueryParams() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
