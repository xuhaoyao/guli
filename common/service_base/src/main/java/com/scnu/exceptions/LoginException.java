package com.scnu.exceptions;

public class LoginException extends RuntimeException {
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
