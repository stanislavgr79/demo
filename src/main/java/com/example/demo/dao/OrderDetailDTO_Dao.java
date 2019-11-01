package com.example.demo.dao;

import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;

public interface OrderDetailDTO_Dao {

    void addProductToOrderDetailDTO(Basket basket, Product product);
}
