package com.goktan.ecommercebackend.serviceImpl;

import com.goktan.ecommercebackend.exception.ProductException;
import com.goktan.ecommercebackend.model.Cart;
import com.goktan.ecommercebackend.model.CartItem;
import com.goktan.ecommercebackend.model.Product;
import com.goktan.ecommercebackend.model.User;
import com.goktan.ecommercebackend.repository.CartRepository;
import com.goktan.ecommercebackend.request.AddItemRequest;
import com.goktan.ecommercebackend.service.CartItemService;
import com.goktan.ecommercebackend.service.CartService;
import com.goktan.ecommercebackend.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());
        CartItem isPresent = cartItemService.isCartItemExist(cart,product, req.getSize(),userId);
        if (isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            int price = req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());
            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item add to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        int totalPrice = 0;
        int totalDiscountedPrice=0;
        int totalItem=0;
        for (CartItem cartItem:cart.getCartItems()){
            totalPrice = totalPrice+cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice+cartItem.getDiscountedPrice();
            totalItem=totalItem+cartItem.getQuantity();
        }
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice-totalDiscountedPrice);
        return cartRepository.save(cart);
    }
}
