package com.apps.ecom.payloads;

import com.apps.ecom.entities.Product;
import com.apps.ecom.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
public class WishlistDto {

    private Integer wishlistId;

    private UserDto user;

    private ProductDto product;

}
