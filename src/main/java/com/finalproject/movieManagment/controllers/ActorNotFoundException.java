package com.finalproject.movieManagment.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ActorNotFoundException extends RuntimeException {
    @ControllerAdvice
    static class ActorNotFoundHandler {
        @ResponseBody //this advice is rendered straight into the response body.
        @ExceptionHandler(ActorNotFoundException.class)
        //configures the advice to only respond if an ProductNotFoundException
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String ActorNotFoundHandler(ActorNotFoundException ex) {
            return ex.getMessage();
        }
    }

    ActorNotFoundException(Long id) {
        super("Actor " + id + " was not found");
    }
}