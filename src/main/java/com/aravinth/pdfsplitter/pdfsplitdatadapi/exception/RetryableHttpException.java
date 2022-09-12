package com.aravinth.pdfsplitter.pdfsplitdatadapi.exception;


import org.springframework.web.client.HttpServerErrorException;

public class RetryableHttpException extends RuntimeException{
    public RetryableHttpException(String message, HttpServerErrorException e){
        super(message ,e);
    }
}
