package com.apps.ecom.services;

import com.apps.ecom.payloads.CartDto;
import com.apps.ecom.payloads.CartResponse;

import java.util.List;

public interface CartService {

    CartDto addToCart(Integer userId, Integer productId, Integer quantity);

    List<CartDto> getAllCart();

    CartResponse getCartByUser(Integer userId);

    void deleteProductFromCart(Integer cartId);

}
