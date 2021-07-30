package com.scnu.exceptions;

public class TeacherException extends RuntimeException{
    public TeacherException() {
        super();
    }

    public TeacherException(String message) {
        super(message);
    }
}
