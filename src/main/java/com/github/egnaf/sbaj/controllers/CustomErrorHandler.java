package com.github.egnaf.sbaj.controllers;

import com.github.egnaf.sbaj.dto.ErrorHandlerResponse;
import com.github.egnaf.sbaj.exceptions.AlreadyExistsException;
import com.github.egnaf.sbaj.exceptions.InvalidDataException;
import com.github.egnaf.sbaj.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "No content")
    public ErrorHandlerResponse handleNotFoundException(NotFoundException e) {
        return new ErrorHandlerResponse(HttpStatus.NO_CONTENT.value(), e.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request")
    public ErrorHandlerResponse handleAlreadyExistsException(AlreadyExistsException e) {
        return new ErrorHandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Expired or invalid JWT token")
    public ErrorHandlerResponse handleInvalidDataException(InvalidDataException e) {
        return new ErrorHandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
