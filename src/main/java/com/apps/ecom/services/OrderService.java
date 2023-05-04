package com.apps.ecom.services;

import com.apps.ecom.payloads.CartDto;
import com.apps.ecom.payloads.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> createOrder(List<CartDto> cartDto, Integer userId, Integer paymentMethod);

}
