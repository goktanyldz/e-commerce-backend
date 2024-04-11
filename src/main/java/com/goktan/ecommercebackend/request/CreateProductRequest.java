package com.goktan.ecommercebackend.request;

import com.goktan.ecommercebackend.model.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
public class CreateProductRequest {
    private String title;
    private  String description;
    private  int price;

    private String brand;
    private  int discountedPrice;

    private int discountPersent;

    private int quantity;

    private String color;

    private Set<Size> size = new HashSet<>();

    private  String imageUrl;

    private String topLevelCategory;
    private String secLevelCategory;
    private String thridLevelCategory;

}
