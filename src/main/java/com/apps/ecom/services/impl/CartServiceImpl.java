package com.apps.ecom.services.impl;

import com.apps.ecom.entities.Cart;
import com.apps.ecom.entities.Product;
import com.apps.ecom.entities.User;
import com.apps.ecom.exceptions.ProductIsExistException;
import com.apps.ecom.exceptions.ResourceNotFoundException;
import com.apps.ecom.payloads.CartDto;
import com.apps.ecom.payloads.CartResponse;
import com.apps.ecom.repositories.CartRepo;
import com.apps.ecom.repositories.ProductRepo;
import com.apps.ecom.repositories.UserRepo;
import com.apps.ecom.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addToCart(Integer userId, Integer productId, Integer quantity) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        Product product = this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "productId", productId));
        Double netAmount = product.getPrice() * quantity;
        Cart isProductExist = this.cartRepo.findByUserAndProduct(user, product);

        if(isProductExist != null){
            this.cartRepo.delete(isProductExist);
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);
        cart.setNetAmount(netAmount);
        Cart addToCart = this.cartRepo.save(cart);
        return this.modelMapper.map(addToCart, CartDto.class);
    }

    @Override
    public List<CartDto> getAllCart() {
        List<Cart> allCart = this.cartRepo.findAll();
        List<CartDto> cartDtoS = allCart.stream().map((cart)->this.modelMapper.map(cart, CartDto.class)).collect(Collectors.toList());
        return cartDtoS;
    }

    @Override
    public CartResponse getCartByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        List<Cart> allCart = this.cartRepo.findByUser(user);
        Integer quantity = allCart.stream().mapToInt((cart)-> cart.getQuantity()).sum();
        Double totalAmount = allCart.stream().mapToDouble((cart)-> cart.getNetAmount()).sum();
        List<CartDto> cartDtoS = allCart.stream().map((cart)->this.modelMapper.map(cart, CartDto.class)).collect(Collectors.toList());

        CartResponse cartResponse = new CartResponse();
        cartResponse.setContent(cartDtoS);
        cartResponse.setTotalQuantity(quantity);
        cartResponse.setTotalAmount(totalAmount);
        return cartResponse;
    }

    @Override
    public void deleteProductFromCart(Integer cartId) {
        Cart cart = this.cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart", "cartId", cartId));;
        this.cartRepo.delete(cart);
    }
}
