package com.goktan.ecommercebackend.serviceImpl;

import com.goktan.ecommercebackend.enums.OrderStatus;
import com.goktan.ecommercebackend.enums.PaymentStatus;
import com.goktan.ecommercebackend.exception.OrderException;
import com.goktan.ecommercebackend.model.*;
import com.goktan.ecommercebackend.repository.*;
import com.goktan.ecommercebackend.service.CartService;
import com.goktan.ecommercebackend.service.OrderService;
import com.goktan.ecommercebackend.service.ProductService;
import org.aspectj.weaver.ast.Literal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private CartRepository cartRepository;
    private CartService cartService;
    private ProductService productService;
    private UserRepository userRepository;
    private AdressRepository adressRepository;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;

    public OrderServiceImpl(CartRepository cartRepository, CartService cartService, ProductService productService,AdressRepository adressRepository,UserRepository userRepository,OrderItemRepository orderItemRepository,OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.adressRepository = adressRepository;
        this.userRepository = userRepository;
        this.orderItemRepository= orderItemRepository;
        this.orderRepository =orderRepository;
    }

    @Override
    public Order createOrder(User user, Adress shippingAdress) {
        shippingAdress.setUser(user);
        Adress adress = adressRepository.save(shippingAdress);
        user.getAdress().add(adress);
        userRepository.save(user);
        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setSize(cartItem.getSize());
            orderItem.setUserId(cartItem.getUserId());
            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);

        }
        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());
        createdOrder.setShippingAddress(adress);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setPaymentStatus("PENDING");
        createdOrder.setCreateAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(createdOrder);
        for(OrderItem item: orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;

    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> optional = orderRepository.findById(orderId);
        if (optional.isPresent()){
            return optional.get();
        }
        throw new OrderException("order not exist with id: "+orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<Order> orderHistory = orderRepository.getUsersOrders(userId);
        return orderHistory;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setPaymentStatus("COMPLETED");
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return  orderRepository.save(order);
    }

    @Override
    public Order cancelledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
            Order order = findOrderById(orderId);
            orderRepository.deleteById(orderId)  ;
    }
}
