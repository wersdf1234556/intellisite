package org.tonzoc.exception;

//model没找到
public class ModelNotFoundException extends Exception {

    public ModelNotFoundException(String message) {
        super(message);
    }
}
