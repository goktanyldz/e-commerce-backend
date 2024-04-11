package com.goktan.ecommercebackend.service;

import com.goktan.ecommercebackend.exception.ProductException;
import com.goktan.ecommercebackend.model.Cart;
import com.goktan.ecommercebackend.model.User;
import com.goktan.ecommercebackend.request.AddItemRequest;

public interface CartService {
    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;


    public Cart findUserCart(Long userId);




}
