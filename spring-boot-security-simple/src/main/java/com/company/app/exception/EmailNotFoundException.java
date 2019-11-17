package com.company.app.exception;

public class EmailNotFoundException extends NotFoundException {

    public EmailNotFoundException(String email) {
        super("The email '" + email + "' is not found Exception");
    }
}
