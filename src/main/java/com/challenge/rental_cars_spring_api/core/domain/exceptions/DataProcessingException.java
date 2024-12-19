package com.challenge.rental_cars_spring_api.core.domain.exceptions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message) {
        super(message);
    }
}
