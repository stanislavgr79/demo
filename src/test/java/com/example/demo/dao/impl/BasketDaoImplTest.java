package com.example.demo.dao.impl;

import com.example.demo.dao.BasketDao;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import org.apache.commons.math3.util.Precision;
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
public class BasketDaoImplTest {

    private static Basket basket;

    static class TestDataStorage {
        Product product1 = new Product( 12L, "Milk", "easy", 10.1, true);
        Product product2 = new Product( 15L, "Meat", "easy", 5.2, true);
        OrderDetailDTO orderDetailDTO1 = OrderDetailDTO.builder().product(product1).quantity(2).price(20.2).build();
        OrderDetailDTO orderDetailDTO2 = OrderDetailDTO.builder().product(product2).quantity(3).price(15.6).build();
        List<OrderDetailDTO> orderDetailDTOList = Arrays.asList(orderDetailDTO1, orderDetailDTO2);
    }

    @Parameterized.Parameters
    private static List<OrderDetailDTO> data() {
        return new ArrayList<>(new TestDataStorage().orderDetailDTOList);
    }

    @Autowired
    private BasketDao basketDao;

    @Before
    public void setUp() {
        basket = Basket.builder().orderDetail(data()).build();
    }

    @After
    public void tearDown() {
       basket = null;
    }

    @Test
    public void shouldClearOrderDetailListInBasket() {
        basketDao.clearOrderDetailList(basket);

        assertEquals(basket.getOrderDetail().size(), 0);
        assertEquals(basket.getTotalQuantity(), 0);
        assertThat(basket.getTotalPrice()).isEqualTo(0);
    }

    @Test
    public void shouldRemoveOrderDetailDTOById() {
        basketDao.removeOrderDetailDTOById(basket, 12L);

        assertEquals(basket.getOrderDetail().size(), 1);
        assertEquals(basket.getTotalQuantity(), 3);
        assertThat(basket.getTotalPrice()).isEqualTo(15.6);
        assertThat(basket.getOrderDetail().get(0)).isEqualTo(new TestDataStorage().orderDetailDTO2);
    }

    @Test
    public void shouldReturnBasketTotalPrice() {
        double result = basketDao.getBasketTotalPriceFromStreamOrderDetails(basket);
        assertThat(result).isEqualTo(35.8);
    }

    @Test
    public void shouldReturnBasketTotalQuantity() {
        int result = basketDao.getBasketTotalQuantityFromStreamOrderDetails(basket);
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void shouldUpdateSubPriceInOrderDetailListInBasket() {
        basketDao.updateSubPriceInOrderDetailListOfBasket(basket);
        double result = basket.getOrderDetail().stream()
                .mapToDouble(OrderDetailDTO::getPrice)
                .sum();
        assertThat(Precision.round(result, 2)).isEqualTo(35.8);
    }
}