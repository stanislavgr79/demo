package com.example.demo.service.impl;

import com.example.demo.dao.repository.ProductRepository;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.ProductService;

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
        return productRepository.findAllByEnabledTrueOrderByProductName();
    }

    @Override
    public List<Product> getAllDisabledProducts() {
        return productRepository.findAllByEnabledFalseOrderByProductName();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        return productRepository.getById(productId);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.getById(productId);
        product.setEnabled(false);
        productRepository.save(product);
    }

    @Override
    public void addProduct(Product product) {
        Product current = productRepository.findByProductName(product.getProductName());
        if (current != null){
            current.setDescription(product.getDescription());
            current.setProductPrice(product.getProductPrice());
            current.setEnabled(true);
            productRepository.save(current);
        } else {
            productRepository.save(product);
        }
    }

    @Override
    public void editProduct(Product product) {
        productRepository.save(product);
    }

}
