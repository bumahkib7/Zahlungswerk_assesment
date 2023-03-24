package com.bbmk.payment_process.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){
    super(message);
    }
}
