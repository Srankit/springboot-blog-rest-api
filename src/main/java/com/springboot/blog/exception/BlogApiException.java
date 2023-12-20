package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends  RuntimeException{

    private HttpStatus status;
    private  String message;

    public BlogApiException(HttpStatus status) {
        this.status = status;
    }

    public BlogApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
