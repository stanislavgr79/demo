package com.example.demo.domain.logic;

import com.example.demo.domain.model.Basket;

public interface BasketLogic {

    void clearOrderDetailList(Basket basket);

    void removeOrderDetailDTOById(Basket basket, Long id);

    double getBasketTotalPriceFromStreamOrderDetails(Basket basket);

    int getBasketTotalQuantityFromStreamOrderDetails(Basket basket);

    void updateSubPriceInOrderDetailListOfBasket(Basket basket);
}
