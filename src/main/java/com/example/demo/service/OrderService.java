package com.example.demo.service;

import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;

import java.util.List;

public interface OrderService {

    Order getOrderById(Long id);

    void saveOrder(Order order);

    List<OrderDetail> getListOrderDetailByOrderId(Long orderId);

    List<Order> getListOrder();
}
