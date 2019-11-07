package com.example.demo.service;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.domain.model.Basket;

import java.util.List;

public interface OrderService {

    Order getOrderById(Long id);

    void saveOrder(Order order);

    void createOrderFromBasket(Customer customer, Basket basket);

    List<Order> findAllByCustomerNotNullOrderByOrderCreateDateDesc();

    List<Order> findAllByCustomer_IdOrderByOrderCreateDateDesc(Long id);

    void updateOrder(String managerName, Order order);
}
