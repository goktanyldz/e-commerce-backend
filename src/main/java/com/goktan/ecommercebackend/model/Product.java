package com.goktan.ecommercebackend.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private int price;

    @Column(name = "discounted_price")
    private int discountedPrice;
    @Column(name = "discount_persent")
    private int discountPersent;

    @Column(name = "quantity")

    private int quantity;
    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;


    @ElementCollection
    @Column(name = "size")
    private Set<Size> sizes = new HashSet<>();

    @Column(name = "image_url")
    private  String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAd;





}
