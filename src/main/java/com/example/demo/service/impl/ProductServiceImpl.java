package com.example.demo.service.impl;

import com.example.demo.dao.repository.ProductRepository;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.ProductService;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return Lists.newArrayList(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        return productRepository.getById(productId);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void editProduct(Product product) {
        productRepository.save(product);
    }

    // example sql native query
    @Override
    public void updateProduct(Product product) {
        productRepository.updateProduct(product.getId(), product.getProductName(),
                product.getProductPrice(), product.getDescription());
    }
}
