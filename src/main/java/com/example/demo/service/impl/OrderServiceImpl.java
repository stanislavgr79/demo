package com.example.demo.service.impl;

import com.example.demo.dao.repository.OrderDetailRepository;
import com.example.demo.dao.repository.OrderRepository;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetail> getListOrderDetailByOrderId(Long orderId) {
        return orderDetailRepository.findOrderDetailsByOrder_Id(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getListOrder() {
        return orderRepository.getOrdersByCustomerNotNull();
    }

}
