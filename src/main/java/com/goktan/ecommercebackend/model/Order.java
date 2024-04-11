package com.goktan.ecommercebackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate ;

    private LocalDateTime deliveryDate;
   @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();
   private double totalPrice;
   private Integer totalDiscountedPrice;

   private  Integer discount;
   private String orderStatus;
   @OneToOne
   private Adress shippingAddress;

   private int totalItem;

   private LocalDateTime createAt;







  }
