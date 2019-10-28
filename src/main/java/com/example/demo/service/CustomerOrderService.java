package com.example.demo.service;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.CustomerOrder;
import com.example.demo.domain.entity.shop.Order;

public interface CustomerOrderService {

    void saveCustomerOrder(CustomerOrder customerOrder);
    double getCustomerOrderTotalPrice(Long basketId);
    int getCustomerOrderTotalQuantity(Long basketId);
    CustomerOrder getDistinctByOrderId(Long id);
}
