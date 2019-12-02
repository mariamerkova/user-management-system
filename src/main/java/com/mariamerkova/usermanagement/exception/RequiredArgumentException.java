package com.mariamerkova.usermanagement.exception;

public class RequiredArgumentException extends RuntimeException {
        public RequiredArgumentException() {
            super("Username and password are required fields");
        }

}
