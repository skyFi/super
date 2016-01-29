package com.skylor.superman.exception;

/**
 * Created by darcy on 2016/1/29.
 */
public class RestServiceRequestException extends RuntimeException {

    public RestServiceRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}