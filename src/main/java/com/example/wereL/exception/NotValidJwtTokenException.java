package com.example.wereL.exception;

public class NotValidJwtTokenException extends RuntimeException {
    public NotValidJwtTokenException() {
        super("Not valid jwt token");
    }

    public NotValidJwtTokenException(String message) {
        super(message);
    }

    public NotValidJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidJwtTokenException(Throwable cause) {
        super(cause);
    }
}
