package org.tonzoc.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.exception.response.ExceptionResponse;

@ControllerAdvice
public class NoOneResultFoundExceptionHandler implements IExceptionHandler{
    @Override
    @ExceptionHandler(NotOneResultFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(Exception exception) {
        return new ExceptionResponse("notOneResultFound_error", exception.getMessage());
    }
}