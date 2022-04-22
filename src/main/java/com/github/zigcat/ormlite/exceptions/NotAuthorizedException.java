package com.github.zigcat.ormlite.exceptions;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String message){
        super(message);
    }
}
