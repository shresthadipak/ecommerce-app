package com.apps.ecom.repositories;

import com.apps.ecom.entities.Product;
import com.apps.ecom.entities.User;
import com.apps.ecom.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {

    List<Wishlist> findByUser(User user);

    Wishlist findByUserAndProduct(User user, Product product);

}
