package com.apps.ecom.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class OrderDto {

    private Integer orderId;

    private UserDto user;

    private ProductDto product;

    private Integer quantity;

    private Double netAmount;

    private Integer paymentMethod;

    private Integer status;

    private Date orderDate;

}
