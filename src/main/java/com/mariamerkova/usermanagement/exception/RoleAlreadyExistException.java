package com.mariamerkova.usermanagement.exception;

public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException() {
        super("Role already exist!");
    }
}
