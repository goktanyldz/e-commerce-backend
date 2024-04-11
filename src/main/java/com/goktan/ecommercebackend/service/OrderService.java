package com.goktan.ecommercebackend.service;

import com.goktan.ecommercebackend.exception.OrderException;
import com.goktan.ecommercebackend.model.Adress;
import com.goktan.ecommercebackend.model.Order;
import com.goktan.ecommercebackend.model.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user, Adress shippingAdress);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

   public Order cancelledOrder(Long orderId) throws OrderException;

   public List<Order> getAllOrders();

   public void deleteOrder(Long orderId) throws OrderException;

}
