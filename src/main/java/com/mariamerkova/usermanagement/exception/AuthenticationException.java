package com.mariamerkova.usermanagement.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("User with this username already exist");
    }
}
