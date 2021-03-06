package com.example.demo.service;

import com.example.demo.domain.model.Basket;

public interface BasketService {

    void addProductToBasket (Basket basket, Long productId);

    void clearOrderDetailList(Basket basket);

    void removeOrderDetailByProductId(Basket basket, Long orderDetailByProductId);

    void updateInfoPriceAndQuantityInBasketSession(Basket basket, Basket basketSession);

    void updateInfoPriceAndQuantityInBasket(Basket basket);
}
