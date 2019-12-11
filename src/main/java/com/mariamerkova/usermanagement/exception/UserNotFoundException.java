package com.mariamerkova.usermanagement.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find user" + id);
    }

    public UserNotFoundException() {

    }
}
