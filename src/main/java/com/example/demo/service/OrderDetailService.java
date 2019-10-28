package com.example.demo.service;


import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;

public interface OrderDetailService {

    void saveOrderDetail(OrderDetail orderDetail);
    void removeOrderDetail(Long productOrderId);
    void removeAllOrdersDetail(Order order);
    void addOrderDetail(Customer customer, Long productId);
}
