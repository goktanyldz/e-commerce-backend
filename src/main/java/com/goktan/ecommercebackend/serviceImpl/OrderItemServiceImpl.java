package com.goktan.ecommercebackend.serviceImpl;

import com.goktan.ecommercebackend.model.OrderItem;
import com.goktan.ecommercebackend.repository.OrderItemRepository;
import com.goktan.ecommercebackend.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private OrderItemRepository orderItemRepository;
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);

    }
}
