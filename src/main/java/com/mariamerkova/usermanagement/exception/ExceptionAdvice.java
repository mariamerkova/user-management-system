package com.mariamerkova.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(RequiredArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String requiredArgumentHandler(RequiredArgumentException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FOUND)
    String authenticationExceptionHandler(AuthenticationException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RequiredMinSizePasswordException.class)
    @ResponseStatus(HttpStatus.LENGTH_REQUIRED)
    String requiredMinSIzePasswordHandler(RequiredMinSizePasswordException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PrivilegeAlreadyExistException.class)
    @ResponseStatus(HttpStatus.FOUND)
    String privilegeAlreadyExistHandler(PrivilegeAlreadyExistException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PrivilegeNotFoundExcepion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String privilegeAlreadyExistHandler(PrivilegeNotFoundExcepion ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RoleAlreadyExistException.class)
    @ResponseStatus(HttpStatus.FOUND)
    String roleAlreadyExistHandler(RoleAlreadyExistException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String roleNotFoundHandler(RoleNotFoundException ex) {
        return ex.getMessage();
    }

}
