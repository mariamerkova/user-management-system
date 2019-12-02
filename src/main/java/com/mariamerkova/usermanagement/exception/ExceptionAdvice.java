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


}
