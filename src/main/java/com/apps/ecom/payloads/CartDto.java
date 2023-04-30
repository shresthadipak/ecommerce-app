package com.apps.ecom.payloads;

import com.apps.ecom.entities.Product;
import com.apps.ecom.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CartDto {

    private Integer cartId;

    private UserDto user;

    private ProductDto product;

    private Integer quantity;

    private Double netAmount;

}
