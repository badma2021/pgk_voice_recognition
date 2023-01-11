package com.example.wereL.exception;

public class UserAlreadyExistRunTimeException extends RuntimeException{
    public UserAlreadyExistRunTimeException() {
        super("User already exist");
    }

    public UserAlreadyExistRunTimeException(String message) {
        super(message);
    }

    public UserAlreadyExistRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistRunTimeException(Throwable cause) {
        super(cause);
    }
}
