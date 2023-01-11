package com.example.wereL.exception;

public class IncorrectJwtTokenException extends RuntimeException{
    public IncorrectJwtTokenException() {
        super();
    }

    public IncorrectJwtTokenException(String message) {
        super(message);
    }

    public IncorrectJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectJwtTokenException(Throwable cause) {
        super(cause);
    }
}
