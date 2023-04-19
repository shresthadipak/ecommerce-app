package com.apps.ecom.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Integer productId;
    private String title;
    private Integer quantity;
    private Long price;
    private String description;
    private String productImage;

}
