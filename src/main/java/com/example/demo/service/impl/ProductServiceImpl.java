package com.example.demo.service.impl;

import com.example.demo.dao.repository.ProductRepository;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        List<Product> productList = productRepository.findAllByEnabledTrueOrderByProductName();
        logger.info("FindAllProduct with status Enabled, productList_size= " + productList.size());
        return productList;
    }

    @Override
    public List<Product> getAllDisabledProducts() {
        List<Product> productList = productRepository.findAllByEnabledFalseOrderByProductName();
        logger.info("FindAllProduct with status Disabled, productList_size= " + productList.size());
        return productList;
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        Product product = productRepository.getById(productId);
        logger.info("Product find by id, product= " + product);
        return product;
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.getById(productId);
        product.setEnabled(false);
        productRepository.save(product);
        logger.info("Product set status Disable, product= " + product);
    }

    @Override
    public void addProduct(Product product) {
        Product current = productRepository.findByProductName(product.getProductName());
        if (current != null){
            current.setDescription(product.getDescription());
            current.setProductPrice(product.getProductPrice());
            current.setEnabled(true);
            productRepository.save(current);
            logger.info("Product NotNew, edit successfully, product= " + product);
        } else {
            productRepository.save(product);
            logger.info("Product New add successfully, product= " + product);
        }
    }

    @Override
    public void editProduct(Product product) {
        productRepository.save(product);
        logger.info("Product edit successfully, product= " + product);
    }

}
