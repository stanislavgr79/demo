package com.example.demo.service.impl;

import com.example.demo.dao.repository.ProductRepository;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.ProductService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product expectedProduct;
    private Product actualProduct;
    private List<Product> expectedProducts;
    private List<Product> expectedProductsDisabled;

    private static class TestDataStorage{
        Product expectedProduct = new Product(12L, "Milk", "easy", 2, true);
        Product product2 = new Product(22L, "Book", "easy", 3, true);
        List<Product> expectedProducts = Arrays.asList(expectedProduct, product2);
        Product actualProduct = new Product(12L, "Milk", "easy", 2, false);
        Product product4 = new Product(22L, "Book", "easy", 3, false);
        List<Product> expectedProductsDisabled = Arrays.asList(actualProduct, product4);
    }

    @Before
    public void setUp() {
        expectedProducts = new TestDataStorage().expectedProducts;
        expectedProductsDisabled = new TestDataStorage().expectedProductsDisabled;
        expectedProduct = new TestDataStorage().expectedProduct;
        actualProduct = new TestDataStorage().actualProduct;
    }

    @After
    public void tearDown() {
        expectedProducts = null;
        expectedProductsDisabled = null;
        expectedProduct = null;
        actualProduct = null;
    }

    @Test
    public void shouldReturnListAllProducts_withStatusIsEnable() {
        when(productRepository.findAllByEnabledTrueOrderByProductName()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllProducts();

        verify(productRepository, times(1)).findAllByEnabledTrueOrderByProductName();
        assertNotNull(actualProducts);
        assertEquals(actualProducts.size(), 2);
        assertThat(actualProducts).isEqualTo(expectedProducts);
    }

    @Test
    public void shouldReturnListAllProducts_withStatusIsDisable() {
        when(productRepository.findAllByEnabledFalseOrderByProductName()).thenReturn(expectedProductsDisabled);

        List<Product> actualProducts = productService.getAllDisabledProducts();

        verify(productRepository, times(1)).findAllByEnabledFalseOrderByProductName();
        assertNotNull(actualProducts);
        assertEquals(actualProducts.size(), 2);
        assertThat(actualProducts).isEqualTo(expectedProductsDisabled);
    }


    @Test
    public void shouldReturnProduct_ByProductId() {
        given(this.productRepository.getById(12L)).willReturn(expectedProduct);

        Product actualProduct = productService.getProductById(12L);

        verify(productRepository, times(1)).getById(12L);
        assertEquals((long) actualProduct.getId(), 12L);
        assertThat(actualProduct).isEqualTo(expectedProduct);
    }

    @Test
    public void shouldChangeStatusProductToDisable_ByProductId() {
        given(this.productRepository.getById(any())).willReturn(expectedProduct);
        when(productRepository.save(any(Product.class))).thenReturn(actualProduct);

        productService.deleteProduct(42L);

        boolean expectedProductEnabled = expectedProduct.isEnabled();
        verify(productRepository, times(1)).getById(42L);
        verify(productRepository, times(1)).save(expectedProduct);
        assertFalse(expectedProductEnabled);
        assertThat(actualProduct).isEqualTo(expectedProduct);
    }

    @Test
    public void shouldUpdateProduct_ProductNotNew_ByProductName() {
        Product baseProduct = new Product( 42L, "Milk", "hard", 12.1, false);

        doReturn(baseProduct).when(productRepository).findByProductName(expectedProduct.getProductName());
        when(productRepository.save(any(Product.class))).thenReturn(baseProduct);

        productService.addProduct(expectedProduct);

        verify(productRepository, times(1)).findByProductName(expectedProduct.getProductName());
        verify(productRepository, times(1)).save(baseProduct);
        assertThat(baseProduct).isEqualTo(expectedProduct);
    }

    @Test
    public void shouldAddProduct_ProductNew_ByProductName() {
        Product baseProduct = null;

        doReturn(baseProduct).when(productRepository).findByProductName(expectedProduct.getProductName());
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);

        productService.addProduct(expectedProduct);

        verify(productRepository, times(1)).findByProductName(expectedProduct.getProductName());
        verify(productRepository, times(1)).save(expectedProduct);
    }

    @Test
    public void shouldUpdateProduct_ByProduct_CallSaveMethodOfProductRepository() {
        Product actualProduct = new Product(12L, "Milk", "easy", 2, true);

        when(productRepository.save(expectedProduct)).thenReturn(actualProduct);

        productService.editProduct(expectedProduct);

        verify(productRepository, times(1)).save(expectedProduct);
        assertThat(actualProduct).isEqualTo(expectedProduct);
    }
}