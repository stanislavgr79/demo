package com.example.demo.service;


import com.example.demo.domain.entity.shop.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    void deleteProduct(Long productId);

    void addProduct(Product product);

    void editProduct(Product product);

    //sql native query example
    void updateProduct(Product product);
}
