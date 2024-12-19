package com.challenge.rental_cars_spring_api.core.domain.exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
