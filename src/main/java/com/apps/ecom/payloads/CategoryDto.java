package com.apps.ecom.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryDto {

    private Integer categoryId;

    @NotBlank
    @Size(min = 3, max = 50)
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String categoryDescription;

}
