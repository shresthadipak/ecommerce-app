package com.apps.ecom.exceptions;

import lombok.Data;

@Data
public class ProductIsExistException extends RuntimeException{
    String message;

    public ProductIsExistException(String message){
        super(String.format(message));
        this.message = message;
    }
}