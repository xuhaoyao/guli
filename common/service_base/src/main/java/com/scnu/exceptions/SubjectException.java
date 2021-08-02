package com.scnu.exceptions;

public class SubjectException extends RuntimeException{
    public SubjectException() {
        super();
    }

    public SubjectException(String message) {
        super(message);
    }
}
