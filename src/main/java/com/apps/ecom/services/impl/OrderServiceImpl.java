package com.apps.ecom.services.impl;


import com.apps.ecom.entities.Cart;
import com.apps.ecom.entities.Order;
import com.apps.ecom.entities.User;
import com.apps.ecom.exceptions.ResourceNotFoundException;
import com.apps.ecom.payloads.CartDto;
import com.apps.ecom.payloads.OrderDto;
import com.apps.ecom.repositories.CartRepo;
import com.apps.ecom.repositories.OrderRepo;
import com.apps.ecom.repositories.UserRepo;
import com.apps.ecom.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;


    @Override
    public List<OrderDto> createOrder(List<CartDto> cartDto, Integer userId, Integer paymentMethod) {

        List<Order> saveOrders = new ArrayList<>();
        for(CartDto cartDto1: cartDto){
            OrderDto orderDto = new OrderDto();
            orderDto.setUser(cartDto1.getUser());
            orderDto.setProduct(cartDto1.getProduct());
            orderDto.setQuantity(cartDto1.getQuantity());
            orderDto.setNetAmount(cartDto1.getNetAmount());
            orderDto.setPaymentMethod(paymentMethod);
            orderDto.setOrderDate(new Date());
            if (paymentMethod == 0){
                orderDto.setStatus(0);
            }else{
                orderDto.setStatus(1);
            }

            // save to order table
            Order order = this.modelMapper.map(orderDto, Order.class);
            Order saveOrder = this.orderRepo.save(order);
            saveOrders.add(saveOrder);
        }

        // remove the product from cart
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        this.cartRepo.deleteByUser(user);


        List<OrderDto> orderDtoS = saveOrders.stream().map((order)->this.modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtoS;

    }

}
