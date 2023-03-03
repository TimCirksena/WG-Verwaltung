package com.wg.boundary.rest.exceptionMappers;

/**
 * @author majaesch
 *
 * */
public class CustomPersistenceException extends RuntimeException{
    public CustomPersistenceException(String message){
        super(message);
    }
}


