package com.github.egnaf.aas.controllers;

import com.github.egnaf.aas.transfers.errors.ErrorHandlerResponse;
import com.github.egnaf.aas.exceptions.AlreadyExistsException;
import com.github.egnaf.aas.exceptions.InvalidDataException;
import com.github.egnaf.aas.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ErrorHandlerResponse handleNotFoundException(NotFoundException e) {
        return new ErrorHandlerResponse(HttpStatus.NO_CONTENT.value(), e.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorHandlerResponse handleAlreadyExistsException(AlreadyExistsException e) {
        return new ErrorHandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorHandlerResponse handleInvalidDataException(InvalidDataException e) {
        return new ErrorHandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
