package com.example.demo.service.impl;

import com.example.demo.dao.repository.ProductRepository;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.ProductService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImplTest.class);

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void shouldReturnListAllProducts_withStatusIsEnable() {

        Product product1 = new Product(12L, "Milk", "easy", 2, true);
        Product product2 = new Product(22L, "Book", "easy", 3, true);
        List<Product> expectedProducts = Arrays.asList(product1, product2);

        when(productRepository.findAllByEnabledTrueOrderByProductName()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllProducts();

        verify(productRepository, times(1)).findAllByEnabledTrueOrderByProductName();
        assertNotNull(actualProducts);
        assertEquals(actualProducts.size(), 2);
        assertThat(actualProducts).isEqualTo(expectedProducts);
        logger.info("ExpectedProducts_size= " + expectedProducts.size() +
                " ActualProducts_size= " + actualProducts.size());
    }

    @Test
    public void shouldReturnListAllProducts_withStatusIsDisable() {
        Product product1 = new Product(12L, "Milk", "easy", 2, false);
        Product product2 = new Product(22L, "Book", "easy", 3, false);
        List<Product> expectedProducts = Arrays.asList(product1, product2);

        when(productRepository.findAllByEnabledFalseOrderByProductName()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllDisabledProducts();

        verify(productRepository, times(1)).findAllByEnabledFalseOrderByProductName();
        assertNotNull(actualProducts);
        assertEquals(actualProducts.size(), 2);
        assertThat(actualProducts).isEqualTo(expectedProducts);
        logger.info("ExpectedProducts_size= " + expectedProducts.size() +
                " ActualProducts_size= " + actualProducts.size());
    }


    @Test
    public void shouldReturnProduct_ByProductId() {
        Product expectedProduct = new Product( 42L, "Milk", "easy", 10.1, true);

        given(this.productRepository.getById(any())).willReturn(expectedProduct);

        Product actualProduct = productService.getProductById(42L);

        verify(productRepository, times(1)).getById(42L);
        assertEquals((long) actualProduct.getId(), 42L);
        assertThat(actualProduct).isEqualTo(expectedProduct);
        logger.info("expected product_id= " + expectedProduct.getId() + ", actual id= " + actualProduct.getId());
    }

    @Test
    public void shouldChangeStatusProductToDisable_ByProductId() {
        Product expectedProduct = new Product( 42L, "Milk", "easy", 10.1, true);
        Product actualProduct = new Product( 42L, "Milk", "easy", 10.1, false);
        logger.info("ExpectedProduct status= " + expectedProduct.isEnabled());

        given(this.productRepository.getById(any())).willReturn(expectedProduct);
        when(productRepository.save(any(Product.class))).thenReturn(actualProduct);

        productService.deleteProduct(42L);

        boolean expectedProductEnabled = expectedProduct.isEnabled();

        verify(productRepository, times(1)).getById(42L);
        verify(productRepository, times(1)).save(expectedProduct);
        assertFalse(expectedProductEnabled);
        assertThat(actualProduct).isEqualTo(expectedProduct);
        logger.info("expected product_status= " + expectedProductEnabled);
    }

    @Test
    public void shouldUpdateProduct_ProductNotNew_ByProductName() {
        Product expectedProduct = new Product( 42L, "Milk", "easy", 10.1, true);
        Product baseProduct = new Product( 42L, "Milk", "hard", 12.1, false);
        logger.info("Before run method: baseProduct= " + baseProduct + ", expectedProduct= " + expectedProduct);

        doReturn(baseProduct).when(productRepository).findByProductName(expectedProduct.getProductName());
        when(productRepository.save(any(Product.class))).thenReturn(baseProduct);

        productService.addProduct(expectedProduct);

        verify(productRepository, times(1)).findByProductName(expectedProduct.getProductName());
        verify(productRepository, times(1)).save(baseProduct);

        assertThat(baseProduct).isEqualTo(expectedProduct);
        logger.info("After run method: baseProduct= " + baseProduct + ", expectedProduct= " + expectedProduct);
        logger.info("Product notNew add successfully");

    }

    @Test
    public void shouldAddProduct_ProductNew_ByProductName() {
        Product expectedProduct = new Product( 42L, "Milk", "easy", 10.1, true);
        Product baseProduct = null;
        logger.info("Before run method: baseProduct= null, expectedProduct= " + expectedProduct);

        doReturn(baseProduct).when(productRepository).findByProductName(expectedProduct.getProductName());
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);

        productService.addProduct(expectedProduct);

        verify(productRepository, times(1)).findByProductName(expectedProduct.getProductName());
        verify(productRepository, times(1)).save(expectedProduct);

        logger.info("After run method: expectedProduct= " + expectedProduct + ". Product New add successfully");
    }


    @Test
    public void shouldUpdateProduct_ByProduct_CallSaveMethodOfProductRepository() {
        Product actualProduct = new Product(42L, "Milk", "easy", 12.1, true);
        Product expectedProducts = new Product(42L, "Milk", "easy", 12.1, true);

        when(productRepository.save(any(Product.class))).thenReturn(actualProduct);

        productService.editProduct(expectedProducts);

        verify(productRepository, times(1)).save(expectedProducts);
        assertThat(actualProduct).isEqualTo(expectedProducts);
        logger.info("Product edit successfully: " + actualProduct);
    }
}