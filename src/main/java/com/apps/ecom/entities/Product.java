package com.apps.ecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "products")
@NoArgsConstructor
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(name="product_title", length = 100, nullable = false)
    private String title;

    private Integer quantity;

    private Long price;

    @Column(length = 10000)
    private String description;

    private String productImage;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
