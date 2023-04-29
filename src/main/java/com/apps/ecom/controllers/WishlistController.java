package com.apps.ecom.controllers;

import com.apps.ecom.payloads.ApiResponse;
import com.apps.ecom.payloads.WishlistDto;
import com.apps.ecom.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/addToWishlist")
    public ResponseEntity<WishlistDto> addToWishlist(
            @RequestParam("userId") Integer userId,
            @RequestParam("productId") Integer productId
    ){
        WishlistDto newWishlistDto = this.wishlistService.addToWishlist(userId, productId);
        return new ResponseEntity<>(newWishlistDto, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<WishlistDto>> getAllWishlist(){
        List<WishlistDto> allWishlist = this.wishlistService.getAllWishlist();
        return ResponseEntity.ok(allWishlist);
    }

    @GetMapping("/getWishlistByUser/user/{userId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByUser(@PathVariable Integer userId){
        List<WishlistDto> allWishlist = this.wishlistService.getWishlistByUser(userId);
        return ResponseEntity.ok(allWishlist);
    }

    @DeleteMapping("/deleteWishlistProduct/{wishlistId}")
    public ResponseEntity<?> deleteWishlistProduct(@PathVariable Integer wishlistId){
        this.wishlistService.deleteWishlist(wishlistId);
        return new ResponseEntity<>(new ApiResponse("Product deleted from wishlist successfully", true), HttpStatus.OK);
    }

}
