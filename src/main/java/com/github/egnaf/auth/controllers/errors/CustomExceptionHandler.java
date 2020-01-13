package com.github.egnaf.auth.controllers.errors;

import com.github.egnaf.auth.exceptions.AlreadyExistsException;
import com.github.egnaf.auth.exceptions.AuthenticationException;
import com.github.egnaf.auth.exceptions.InvalidDataException;
import com.github.egnaf.auth.exceptions.NotFoundException;
import com.github.egnaf.auth.transfers.errors.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        log.debug("{}: {}, status={}", e.getClass().getSimpleName(), e.getMessage(), e.getHttpStatus().value());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), e.getHttpStatus()), e.getHttpStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        log.debug("{}: {}, status={}", e.getClass().getSimpleName(), e.getMessage(), e.getHttpStatus().value());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), e.getHttpStatus()), e.getHttpStatus());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(AlreadyExistsException e) {
        log.debug("{}: {}, status={}", e.getClass().getSimpleName(), e.getMessage(), e.getHttpStatus().value());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), e.getHttpStatus()), e.getHttpStatus());
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<?> handleInvalidDataException(InvalidDataException e) {
        log.debug("{}: {}, status={}", e.getClass().getSimpleName(), e.getMessage(), e.getHttpStatus().value());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), e.getHttpStatus()), e.getHttpStatus());
    }
}
