package com.shantanux0.linkdin.post_service.Execptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
