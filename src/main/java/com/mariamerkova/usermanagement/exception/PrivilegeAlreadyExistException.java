package com.mariamerkova.usermanagement.exception;

public class PrivilegeAlreadyExistException extends RuntimeException {
    public PrivilegeAlreadyExistException() {
        super("This privilege already exist!");
    }
}
