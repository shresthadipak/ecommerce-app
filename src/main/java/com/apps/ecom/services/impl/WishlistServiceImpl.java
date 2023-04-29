package com.apps.ecom.services.impl;

import com.apps.ecom.entities.Product;
import com.apps.ecom.entities.User;
import com.apps.ecom.entities.Wishlist;
import com.apps.ecom.exceptions.ProductIsExistException;
import com.apps.ecom.exceptions.ResourceNotFoundException;
import com.apps.ecom.payloads.WishlistDto;
import com.apps.ecom.repositories.ProductRepo;
import com.apps.ecom.repositories.UserRepo;
import com.apps.ecom.repositories.WishlistRepo;
import com.apps.ecom.services.WishlistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepo wishlistRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WishlistDto addToWishlist(Integer userId, Integer productId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        Product product = this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "productId", productId));
        Wishlist isUserProductExist = this.wishlistRepo.findByUserAndProduct(user, product);

        if(isUserProductExist != null){
            throw new ProductIsExistException("Product already exists in user's wishlist.");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        Wishlist addWishlist = this.wishlistRepo.save(wishlist);
        return this.modelMapper.map(addWishlist, WishlistDto.class);
    }

    @Override
    public List<WishlistDto> getAllWishlist() {
        List<Wishlist> allWishlist = this.wishlistRepo.findAll();
        List<WishlistDto>  wishlistDtos = allWishlist.stream().map((wishlist)->this.modelMapper.map(wishlist, WishlistDto.class)).collect(Collectors.toList());
        return wishlistDtos;
    }

    @Override
    public List<WishlistDto> getWishlistByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
        List<Wishlist> wishlists = this.wishlistRepo.findByUser(user);
        List<WishlistDto> wishlistDtos = wishlists.stream().map((wishlist)->this.modelMapper.map(wishlist, WishlistDto.class)).collect(Collectors.toList());
        return wishlistDtos;
    }

    @Override
    public void deleteWishlist(Integer wishlistId) {
        Wishlist wishlist = this.wishlistRepo.findById(wishlistId).orElseThrow(()-> new ResourceNotFoundException("Wishlist", "wishlistId", wishlistId));
        this.wishlistRepo.deleteById(wishlistId);
    }
}
