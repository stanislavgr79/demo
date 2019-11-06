package com.example.demo.service.impl;

import com.example.demo.dao.repository.CustomerOrderRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.CustomerOrder;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.service.CustomerOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerOrderServiceImpl.class);

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Override
    public void saveCustomerOrder(CustomerOrder customerOrder) {
        customerOrderRepository.save(customerOrder);
        logger.info("CustomerOrder save successfully, CustomerOrder= "+ customerOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerOrder getDistinctByOrderId(Long id){
        CustomerOrder customerOrder = customerOrderRepository.getDistinctByOrderId(id);
        logger.info("CustomerOrder findByOrderId =" + id + " successfully, CustomerOrder= "+ customerOrder);
        return customerOrder;

    }

    @Override
    public void createCustomerOrderByCustomerAndOrder(Customer customer, Order order){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrder(order);
        customerOrder.setCustomer(customer);
        saveCustomerOrder(customerOrder);
    }
}
