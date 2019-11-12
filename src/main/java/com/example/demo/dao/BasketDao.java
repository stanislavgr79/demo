package com.example.demo.dao;

import com.example.demo.domain.model.Basket;

public interface BasketDao {

    void clearOrderDetailList(Basket basket);

    void removeOrderDetailDTOById(Basket basket, Long id);

    double getBasketTotalPriceFromStreamOrderDetails(Basket basket);

    int getBasketTotalQuantityFromStreamOrderDetails(Basket basket);

    void updateSubPriceInOrderDetailListOfBasket(Basket basket);
}
