package com.example.demo.service;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.CustomerOrder;
import com.example.demo.domain.entity.shop.Order;

public interface CustomerOrderService {

    void saveCustomerOrder(CustomerOrder customerOrder);

    void createCustomerOrderByCustomerAndOrder(Customer customer, Order order);
}
