package com.apps.ecom.services;

import com.apps.ecom.payloads.WishlistDto;

import java.util.List;

public interface WishlistService {

    WishlistDto addToWishlist(Integer userId, Integer productId);

    List<WishlistDto> getAllWishlist();

    List<WishlistDto> getWishlistByUser(Integer userId);

    void deleteWishlist(Integer wishlistId);

}
