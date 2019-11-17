package com.example.demo.service.impl;

import com.example.demo.dao.OrderDetailRepository;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.service.OrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
        logger.info("OrderDetail save successfully, orderDetail= " + orderDetail);
    }

}
