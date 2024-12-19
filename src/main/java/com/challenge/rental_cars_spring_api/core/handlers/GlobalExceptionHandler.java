package com.challenge.rental_cars_spring_api.core.handlers;

import com.challenge.rental_cars_spring_api.core.domain.exceptions.FileCannotEmptyException;
import com.challenge.rental_cars_spring_api.core.domain.exceptions.InvalidFileFormatException;
import com.challenge.rental_cars_spring_api.core.domain.exceptions.InvalidPhoneNumberException;
import com.challenge.rental_cars_spring_api.core.domain.exceptions.NotFoundException;
import com.challenge.rental_cars_spring_api.core.queries.dtos.CustomMessageDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e, WebRequest request) {
        CustomMessageDTO error = new CustomMessageDTO(e.getMessage(), HttpStatus.NOT_FOUND.value());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<Object> handleInvalidPhoneNumberException(InvalidPhoneNumberException e, WebRequest request) {
        CustomMessageDTO error = new CustomMessageDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<Object> handleInvalidFileFormatException(InvalidFileFormatException e, WebRequest request) {
        CustomMessageDTO error = new CustomMessageDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(FileCannotEmptyException.class)
    public ResponseEntity<Object> handleFileCannotEmptyException(FileCannotEmptyException e, WebRequest request) {
        CustomMessageDTO error = new CustomMessageDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }


}
