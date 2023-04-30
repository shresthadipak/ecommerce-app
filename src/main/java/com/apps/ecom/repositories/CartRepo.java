package com.apps.ecom.repositories;

import com.apps.ecom.entities.Cart;
import com.apps.ecom.entities.Product;
import com.apps.ecom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Integer> {

    List<Cart> findByUser(User user);

    Cart findByUserAndProduct(User user, Product product);

}
