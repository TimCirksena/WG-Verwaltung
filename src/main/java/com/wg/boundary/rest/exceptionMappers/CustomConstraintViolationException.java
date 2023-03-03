package com.wg.boundary.rest.exceptionMappers;

/**
 * @author majaesch
 *
 * */
public class CustomConstraintViolationException extends RuntimeException{
    public CustomConstraintViolationException(String errorMessage){
        super(errorMessage);
    }
}

