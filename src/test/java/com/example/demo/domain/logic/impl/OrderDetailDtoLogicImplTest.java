package com.example.demo.domain.logic.impl;

import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDtoLogicImplTest {

    @Autowired
    private OrderDetailDtoLogicImpl orderDetailDtoDaoImpl;

    private static Basket basket;
    private static Product product;

    static class TestDataStorage {
        Product product1 = new Product( 12L, "Milk", "easy", 10.1, true);
        Product product2 = new Product( 15L, "Meat", "easy", 5.2, true);
        OrderDetailDTO orderDetailDTO1 = OrderDetailDTO.builder().product(product1).quantity(2).price(20.2).build();
        List<OrderDetailDTO> orderDetailDTOList = Arrays.asList(orderDetailDTO1);
    }

    @Parameterized.Parameters
    private static List<OrderDetailDTO> data() {
        return new ArrayList<>(new TestDataStorage().orderDetailDTOList);
    }

    @Before
    public void setUp() {
        basket = Basket.builder().orderDetail(data()).build();
        product = new TestDataStorage().product2;
    }

    @After
    public void tearDown() {
        basket = null;
        product = null;
    }

    @Test
    public void addProductToOrderDetailDTO() {
        orderDetailDtoDaoImpl.addProductToOrderDetailDTO(basket, product);

        assertEquals(basket.getOrderDetail().size(), 2);
        assertEquals(basket.getTotalQuantity(), 3);
        assertThat(basket.getTotalPrice()).isEqualTo(25.4);
        assertThat(basket.getOrderDetail().get(1).getProduct()).isEqualTo(new TestDataStorage().product2);
    }
}