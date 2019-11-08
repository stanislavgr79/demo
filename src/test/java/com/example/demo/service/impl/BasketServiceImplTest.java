package com.example.demo.service.impl;

import com.example.demo.dao.BasketDao;
import com.example.demo.dao.OrderDetailDtoDao;
import com.example.demo.service.BasketService;
import com.example.demo.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketServiceImplTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private BasketDao basketDao;

    @MockBean
    private OrderDetailDtoDao orderDetailDtoDao;

    @Autowired
    private BasketService basketService;



    @Test
    public void addProductToBasket() {

    }

    @Test
    public void clearOrderDetailList() {
    }

    @Test
    public void removeOrderDetailByProductId() {
    }

    @Test
    public void updateInfoPriceAndQuantityInBasketSession() {
    }

    @Test
    public void updateInfoPriceAndQuantityInBasket() {
    }
}