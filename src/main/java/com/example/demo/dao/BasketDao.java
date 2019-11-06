package com.example.demo.dao;

import com.example.demo.domain.model.Basket;
import org.springframework.stereotype.Repository;


public interface BasketDao {

    void clearOrderDetailList(Basket basket);

    void removeOrderDetailDTOById(Basket basket, Long id);

    double getBasketTotalPrice(Basket basket);

    int getBasketTotalQuantity(Basket basket);

    void updateSubPriceInOrderDetailList(Basket basket);
}
