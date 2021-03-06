package com.example.demo.service.impl;

import com.example.demo.domain.logic.BasketLogic;
import com.example.demo.domain.logic.OrderDetailDtoLogic;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import com.example.demo.service.BasketService;
import com.example.demo.service.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketServiceImplTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private BasketLogic basketLogic;

    @MockBean
    private OrderDetailDtoLogic orderDetailDtoLogic;

    @Autowired
    private BasketService basketService;

    private Basket basket;
    private Basket basketSession;
    private Product expectedProduct;

    private static class TestDataStorage{
        Product product = new Product( 42L, "Milk", "easy", 10.1, true);
        OrderDetailDTO orderDetailDTO = OrderDetailDTO.builder().product(product).quantity(2).price(20.2).build();
        List<OrderDetailDTO> orderDetailDTOList = Collections.singletonList(orderDetailDTO);
        Basket basket = Basket.builder().totalPrice(20.2).totalQuantity(2).orderDetail(orderDetailDTOList).build();
        Basket basketSession = Basket.builder().orderDetail(orderDetailDTOList).build();
    }

    @Before
    public void setUp() {
        basket = new TestDataStorage().basket;
        expectedProduct = new TestDataStorage().product;
        basketSession = new TestDataStorage().basketSession;
    }

    @After
    public void tearDown() {
        basket = null;
        basketSession = null;
        expectedProduct = null;
    }

    @Test
    public void shouldGetProductFromRepositoryAndAddProductToOrderDetailDtoInBasket_ByBasketAndProductId() {
        given(this.productService.getProductById(42L)).willReturn(expectedProduct);
        doNothing().when(orderDetailDtoLogic).addProductToOrderDetailDTO(basket,expectedProduct);

        basketService.addProductToBasket(basket, 42L);

        verify(productService, times(1)).getProductById(42L);
        verify(orderDetailDtoLogic, times(1)).addProductToOrderDetailDTO(basket, expectedProduct);
        assertThat(expectedProduct.getId()).isEqualTo(42L);
    }

    @Test
    public void shouldClearOrderDetailListInBasket_CallClearMethodOfBasketDao() {
        doNothing().when(basketLogic).clearOrderDetailList(any(Basket.class));
        basketService.clearOrderDetailList(basket);
        verify(basketLogic, times(1)).clearOrderDetailList(basket);
    }

    @Test
    public void shouldRemoveOrderDetailInBasket_ByProductId() {
        doNothing().when(basketLogic).removeOrderDetailDTOById(basket, 42L);
        basketService.removeOrderDetailByProductId(basket, 42L);
        verify(basketLogic, times(1)).removeOrderDetailDTOById(basket, 42L);
    }

    @Test
    public void shouldUpdateInfoPriceAndQuantityInBasketSession_ByBasket() {
        doNothing().when(basketLogic).updateSubPriceInOrderDetailListOfBasket(basketSession);
        given(this.basketLogic.getBasketTotalQuantityFromStreamOrderDetails(basket)).willReturn(2);
        given(this.basketLogic.getBasketTotalPriceFromStreamOrderDetails(basket)).willReturn(20.2);

        basketService.updateInfoPriceAndQuantityInBasketSession(basket, basketSession);

        verify(basketLogic, times(1)).updateSubPriceInOrderDetailListOfBasket(basketSession);
        verify(basketLogic, times(1)).getBasketTotalQuantityFromStreamOrderDetails(basket);
        verify(basketLogic, times(1)).getBasketTotalPriceFromStreamOrderDetails(basket);
        assertThat(basket).isEqualTo(basketSession);
    }

    @Test
    public void shouldUpdateInfoPriceAndQuantityInBasket() {
        doNothing().when(basketLogic).updateSubPriceInOrderDetailListOfBasket(basket);
        given(this.basketLogic.getBasketTotalQuantityFromStreamOrderDetails(basket)).willReturn(2);
        given(this.basketLogic.getBasketTotalPriceFromStreamOrderDetails(basket)).willReturn(20.2);

        basketService.updateInfoPriceAndQuantityInBasket(basket);

        verify(basketLogic, times(1)).updateSubPriceInOrderDetailListOfBasket(basket);
        verify(basketLogic, times(1)).getBasketTotalQuantityFromStreamOrderDetails(basket);
        verify(basketLogic, times(1)).getBasketTotalPriceFromStreamOrderDetails(basket);
        assertThat(basket.getTotalQuantity()).isEqualTo(2);
        assertThat(basket.getTotalPrice()).isEqualTo(20.2);
    }
}