package com.finalproject.movieManagment.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RatingNotFoundException extends RuntimeException {
    @ControllerAdvice
    static class RatingNotFoundHandler {
        @ResponseBody //this advice is rendered straight into the response body.
        @ExceptionHandler(RatingNotFoundException.class)
        //configures the advice to only respond if an ProductNotFoundException
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String RatingNotFoundHandler(RatingNotFoundException ex) {
            return ex.getMessage();
        }
    }

    RatingNotFoundException(Long id) {
        super("Rating " + id + " was not found");
    }
    RatingNotFoundException(String message){super(message);}
}