package com.mariamerkova.usermanagement.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super("Cannot find that role!");
    }
}
