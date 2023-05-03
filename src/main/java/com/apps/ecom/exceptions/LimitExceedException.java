package com.apps.ecom.exceptions;

import lombok.Data;

@Data
public class LimitExceedException extends RuntimeException{

    Integer fieldValue;

    public LimitExceedException(Integer fieldValue){
        super(String.format("Product has %s in stock. You are unable to claim required quantity !!", fieldValue));
        this.fieldValue = fieldValue;
    }

}
