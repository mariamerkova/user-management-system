package com.mariamerkova.usermanagement.exception;

public class PrivilegeNotFoundExcepion extends RuntimeException {
    public PrivilegeNotFoundExcepion() {
        super("Privilege not found");
    }
}
