package com.apps.ecom.controllers;

import com.apps.ecom.entities.Order;
import com.apps.ecom.payloads.CartDto;
import com.apps.ecom.payloads.CartResponse;
import com.apps.ecom.payloads.OrderDto;
import com.apps.ecom.services.CartService;
import com.apps.ecom.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @PostMapping("/user/{userId}/createOrder")
    public ResponseEntity<List<OrderDto>> createOrder(
            @PathVariable("userId") Integer userId,
            @RequestParam("paymentMethod") Integer paymentMethod
    ){
        CartResponse cartDetails = this.cartService.getCartByUser(userId);
        List<CartDto> cartDto = cartDetails.getContent();
        List<OrderDto> orderDto = this.orderService.createOrder(cartDto, userId, paymentMethod);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

}
