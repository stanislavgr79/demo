package com.example.demo.domain.logic;

import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;

public interface OrderDetailDtoLogic {

    void addProductToOrderDetailDTO(Basket basket, Product product);
}
