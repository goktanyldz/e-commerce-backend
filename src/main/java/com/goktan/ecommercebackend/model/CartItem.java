package com.goktan.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Product product;

    private String size;
    private int quantity;

    private Integer price;

    private Integer discountedPrice;

    private Long userId;


}
