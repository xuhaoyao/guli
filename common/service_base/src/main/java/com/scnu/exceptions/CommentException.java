package com.scnu.exceptions;

public class CommentException extends RuntimeException{
    public CommentException() {
        super();
    }

    public CommentException(String message) {
        super(message);
    }
}
