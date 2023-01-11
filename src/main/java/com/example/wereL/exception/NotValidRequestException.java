package com.example.wereL.exception;

public class NotValidRequestException extends RuntimeException{
    public NotValidRequestException() {
        super("Not valid request");
        
    }

    public NotValidRequestException(String message) {
        super(message);
    }

    public NotValidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidRequestException(Throwable cause) {
        super(cause);
    }
}
