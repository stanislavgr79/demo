package com.example.demo.service.impl;

import com.example.demo.dao.repository.CustomerOrderRepository;
import com.example.demo.domain.entity.shop.CustomerOrder;
import com.example.demo.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Override
    public void saveCustomerOrder(CustomerOrder customerOrder) {
        customerOrderRepository.save(customerOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerOrder getDistinctByOrderId(Long id){
        return customerOrderRepository.getDistinctByOrderId(id);
    }
}
