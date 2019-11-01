package com.example.demo.service;

import com.example.demo.domain.entity.shop.CustomerOrder;

public interface CustomerOrderService {

    void saveCustomerOrder(CustomerOrder customerOrder);
    CustomerOrder getDistinctByOrderId(Long id);
}
