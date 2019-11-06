package com.example.demo.service.impl;

import com.example.demo.dao.repository.OrderDetailRepository;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrder_Id(Long orderId) {
        return orderDetailRepository.findOrderDetailsByOrder_Id(orderId);
    }

}
