package com.example.demo.service;


import com.example.demo.domain.entity.shop.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    void saveOrderDetail(OrderDetail orderDetail);

    List<OrderDetail> findOrderDetailsByOrder_Id(Long orderId);
}
