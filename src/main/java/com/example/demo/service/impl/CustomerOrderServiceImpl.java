package com.example.demo.service.impl;

import com.example.demo.dao.repository.OrderDetailRepository;
import com.example.demo.dao.repository.CustomerOrderRepository;
import com.example.demo.domain.entity.shop.CustomerOrder;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public void saveCustomerOrder(CustomerOrder customerOrder) {
        customerOrderRepository.save(customerOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public double getCustomerOrderTotalPrice(Long basketId) {

        List<OrderDetail> orderDetail =
                orderDetailRepository.findOrderDetailsByOrder_Id(basketId);

        return  orderDetail.stream()
                .mapToDouble(o-> o.getQuantity()*o.getProductPrice())
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public int getCustomerOrderTotalQuantity(Long basketId) {

        List<OrderDetail> orderDetail =
                orderDetailRepository.findOrderDetailsByOrder_Id(basketId);

        return orderDetail.stream()
                .mapToInt(OrderDetail::getQuantity)
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerOrder getDistinctByOrderId(Long id){
        return customerOrderRepository.getDistinctByOrderId(id);
    };

}
