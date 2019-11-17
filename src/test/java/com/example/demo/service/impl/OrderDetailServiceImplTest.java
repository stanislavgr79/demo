package com.example.demo.service.impl;

import com.example.demo.dao.OrderDetailRepository;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.OrderDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailServiceImplTest {

    @MockBean
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Test
    public void shouldSaveOrderDetail_CallSaveMethodOfOrderDetailRepository() {
        Product product = new Product(22L, "Book", "easy", 3, true);
        Order order = Order.builder().id(1L).build();
        OrderDetail actualOrderDetail = new OrderDetail(12L, 5, 44.4, product, order);
        OrderDetail expectedOrderDetail = new OrderDetail(12L, 5, 44.4, product, order);

        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(actualOrderDetail);

        orderDetailService.saveOrderDetail(expectedOrderDetail);

        verify(orderDetailRepository, times(1)).save(expectedOrderDetail);
        assertThat(actualOrderDetail).isEqualTo(expectedOrderDetail);
    }
}