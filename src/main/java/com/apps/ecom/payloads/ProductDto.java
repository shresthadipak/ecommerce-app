package com.apps.ecom.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Integer productId;

    @NotBlank
    @Size(min=10, max=50)
    private String title;


    @Min(value = 1)
    private Integer quantity;


    @Min(value = 1)
    private Double price;

    @NotBlank
    @Size(min=10, max=1000)
    private String description;

    private String productImage;

    private CategoryDto category;

}
