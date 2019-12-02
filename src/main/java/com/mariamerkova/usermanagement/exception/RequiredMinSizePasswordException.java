package com.mariamerkova.usermanagement.exception;

public class RequiredMinSizePasswordException extends RuntimeException {
    public RequiredMinSizePasswordException() {
        super("The password must be 6 or more symbols");
    }
}
