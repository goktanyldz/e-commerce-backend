package com.goktan.ecommercebackend.service;

import com.goktan.ecommercebackend.exception.CartItemException;
import com.goktan.ecommercebackend.exception.UserException;
import com.goktan.ecommercebackend.model.Cart;
import com.goktan.ecommercebackend.model.CartItem;
import com.goktan.ecommercebackend.model.Product;
import jdk.jshell.spi.ExecutionControl;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws UserException, CartItemException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws  CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;


}
