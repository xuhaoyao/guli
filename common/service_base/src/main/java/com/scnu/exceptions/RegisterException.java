package com.scnu.exceptions;

public class RegisterException extends RuntimeException{
    public RegisterException() {
        super();
    }

    public RegisterException(String message) {
        super(message);
    }
}
