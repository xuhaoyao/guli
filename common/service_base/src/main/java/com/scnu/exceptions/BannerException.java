package com.scnu.exceptions;

public class BannerException extends RuntimeException{
    public BannerException() {
        super();
    }

    public BannerException(String message) {
        super(message);
    }
}
