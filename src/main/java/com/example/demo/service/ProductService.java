package com.example.demo.service;


import com.example.demo.domain.entity.shop.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<Product> getAllDisabledProducts();

    Product getProductById(Long productId);

    void deleteProduct(Long productId);

    void addProduct(Product product);

    void editProduct(Product product);

    Page<Product> findAllByEnabledTrueOrderByProductName(int page);

}
