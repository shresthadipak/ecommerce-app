package com.apps.ecom.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wishlist")
@NoArgsConstructor
@Data
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wishlistId;

    @ManyToOne
    @JoinColumn(name= "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
