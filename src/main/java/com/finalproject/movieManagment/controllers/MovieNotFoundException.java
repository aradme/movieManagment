package com.finalproject.movieManagment.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MovieNotFoundException extends RuntimeException {
    @ControllerAdvice
    static class MovieNotFoundHandler {
        @ResponseBody //this advice is rendered straight into the response body.
        @ExceptionHandler(MovieNotFoundException.class)
        //configures the advice to only respond if an ProductNotFoundException
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String MovieNotFoundHandler(MovieNotFoundException ex) {
            return ex.getMessage();
        }
    }

    MovieNotFoundException(String id) {
        super("Movie " + id + " was not found");
    }
}