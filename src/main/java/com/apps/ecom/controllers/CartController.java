package com.apps.ecom.controllers;

import com.apps.ecom.entities.Cart;
import com.apps.ecom.payloads.ApiResponse;
import com.apps.ecom.payloads.CartDto;
import com.apps.ecom.payloads.CartResponse;
import com.apps.ecom.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<CartDto> addToCart(
            @RequestParam("userId") Integer userId,
            @RequestParam("productId") Integer productId,
            @RequestParam("quantity") Integer quantity
    ){
        CartDto newCart = this.cartService.addToCart(userId, productId, quantity);
        return new ResponseEntity<>(newCart, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CartDto>> getAllCart(){
        List<CartDto> allCart = this.cartService.getAllCart();
        return ResponseEntity.ok(allCart);
    }

    @GetMapping("/getCartByUser/user/{userId}")
    public ResponseEntity<CartResponse> getCartByUser(@PathVariable Integer userId){
        CartResponse cartDto = this.cartService.getCartByUser(userId);
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/deleteProductFromCart/{cartId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Integer cartId){
        this.cartService.deleteProductFromCart(cartId);
        return new ResponseEntity<>(new ApiResponse("Product is deleted from cart successfully !!", true), HttpStatus.OK);
    }

}
