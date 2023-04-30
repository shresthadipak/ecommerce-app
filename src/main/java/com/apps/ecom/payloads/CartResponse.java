package com.apps.ecom.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CartResponse {
    private List<CartDto> content;
    private Integer totalQuantity;
    private Double totalAmount;
}
