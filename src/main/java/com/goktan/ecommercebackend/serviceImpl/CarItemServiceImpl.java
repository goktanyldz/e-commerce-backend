package com.goktan.ecommercebackend.serviceImpl;

import com.goktan.ecommercebackend.exception.CartItemException;
import com.goktan.ecommercebackend.exception.UserException;
import com.goktan.ecommercebackend.model.Cart;
import com.goktan.ecommercebackend.model.CartItem;
import com.goktan.ecommercebackend.model.Product;
import com.goktan.ecommercebackend.model.User;
import com.goktan.ecommercebackend.repository.CartItemRepository;
import com.goktan.ecommercebackend.repository.CartRepository;
import com.goktan.ecommercebackend.repository.UserRepository;
import com.goktan.ecommercebackend.service.CartItemService;
import com.goktan.ecommercebackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarItemServiceImpl implements CartItemService {

    private CartItemRepository cartItemRepository;
    private UserServiceImpl userServiceImpl;
    private CartRepository cartRepository;
    private UserRepository userRepository;



    public CarItemServiceImpl(CartItemRepository cartItemRepository, UserServiceImpl userServiceImpl, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userServiceImpl = userServiceImpl;
        this.cartRepository = cartRepository;

    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        CartItem createdCartItem = cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws UserException, CartItemException {
        CartItem item = findCartItemById(id);
        User user = userServiceImpl.findUserById(userId);
        if (user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());


        }
       return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart,product,size,userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
          CartItem cartItem = findCartItemById(cartItemId);
          User user = userServiceImpl.findUserById(cartItem.getUserId());
          User reqUser = userServiceImpl.findUserById(userId);
          if (user.getId().equals(reqUser.getId())){
              cartItemRepository.deleteById(cartItemId);
          }else {
              throw new UserException("You cant remove another users item");
          }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("cartItem not found with id: "+cartItemId);
    }
}
