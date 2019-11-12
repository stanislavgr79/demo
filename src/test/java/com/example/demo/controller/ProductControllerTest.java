package com.example.demo.controller;

import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.ProductService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ProductControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter springSecurityFilterChain;

    private final String ADMIN = "admin@shop.ru";
    private final String MANAGER = "manager@shop.ru";
    private final String USER = "customer@test.ru";

    private Product expectedProduct;
    private List<Product> expectedProductsEnable;
    private List<Product> expectedProductsDisable;

    private static class TestDataStorage {
        Product product1 = new Product(12L, "Milk", "easy", 2, true);
        Product product2 = new Product(22L, "Book", "easy", 3, true);
        List<Product> expectedProductsTrue = Arrays.asList(product1, product2);
        Product product3 = new Product(12L, "Milk", "easy", 2, false);
        Product product4 = new Product(22L, "Book", "easy", 3, false);
        List<Product> expectedProductsFalse = Arrays.asList(product3, product4);
    }

    @Before
    public void setUp () {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain).dispatchOptions(true).build();
        expectedProduct = new TestDataStorage().product1;
        expectedProductsEnable = new TestDataStorage().expectedProductsTrue;
        expectedProductsDisable = new TestDataStorage().expectedProductsFalse;
    }

    @After
    public void teardown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void shouldReturnListProductsWithStatusEnabled () throws Exception{
        when(productService.getAllProducts()).thenReturn(expectedProductsEnable);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/getAllProducts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.view().name("productList"))
        ;

        MvcResult mvcResult  = result.andReturn();
        List<Product> actualProducts = (List<Product>) mvcResult.getRequest().getAttribute("products");
        verify(productService, times(1)).getAllProducts();
        assertThat(actualProducts).isEqualTo(expectedProductsEnable);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/getAllProducts");
    }

    @Test
    public void shouldReturnListProductsWithStatusDisable() throws Exception{
        when(productService.getAllDisabledProducts()).thenReturn(expectedProductsDisable);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/product/getProductsDisabled")
                        .with(SecurityRequestPostProcessors.userDetailsService(MANAGER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.view().name("productDisableList"))
        ;

        MvcResult mvcResult  = result.andReturn();
        List<Product> actualProducts = (List<Product>) mvcResult.getRequest().getAttribute("products");
        assertThat(expectedProductsDisable).isEqualTo(actualProducts);
        verify(productService, times(1)).getAllDisabledProducts();
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/product/getProductsDisabled");
    }

    @Test
    public void shouldRequestProductFromService_ByLongId() throws Exception{
        when(productService.getProductById(12L)).thenReturn(expectedProduct);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/getProductById/{productId}", "12")
                .with(SecurityRequestPostProcessors.userDetailsService(USER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("productEntity"))
                .andExpect(MockMvcResultMatchers.view().name("productPage"))
                ;

        MvcResult mvcResult  = result.andReturn();
        Product actualProduct = (Product) mvcResult.getRequest().getAttribute("productEntity");
        verify(productService, times(1)).getProductById(12L);
        assertThat(actualProduct).isEqualTo(expectedProduct);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/getProductById/12");
    }

    @Test
    public void shouldSendProductIdToServiceForDisable_LongId() throws Exception {
        doNothing().when(productService).deleteProduct(12L);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/product/delete/{productId}", "12")
                .with(SecurityRequestPostProcessors.userDetailsService(ADMIN)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/getAllProducts"));

        MvcResult mvcResult  = result.andReturn();
        verify(productService, times(1)).deleteProduct(12L);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/product/delete/12");
    }

    @Test
    public void shouldGetNewProductToProductFormPage() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/product/addProduct", "12")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("productForm"))
                .andExpect(MockMvcResultMatchers.view().name("addProduct"))
                ;

        MvcResult mvcResult  = result.andReturn();
        Product actualProduct = (Product) mvcResult.getRequest().getAttribute("productForm");
        assertThat(actualProduct.getId()).isEqualTo(null);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/product/addProduct");
    }

    @Test
    public void shouldSendParameterNewProductToService_ByModel() throws Exception {
        doNothing().when(productService).addProduct(any(Product.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/product/addProduct")
                        .with(SecurityRequestPostProcessors.userDetailsService(MANAGER))
                        .param("productName","Milk")
                        .param("description","easy")
                        .param("productPrice","2"))
                        .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/product/addProduct"));

        MvcResult mvcResult  = result.andReturn();

        verify(productService, times(1)).addProduct(any(Product.class));
        assertThat(mvcResult.getRequest().getParameterMap().get("productName")[0]).isEqualTo("Milk");
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/product/addProduct");
    }

    @Test
    public void shouldRequestProductFromServiceAndSendToEditProductPage_ByLongId() throws Exception {
        when(productService.getProductById(12L)).thenReturn(expectedProduct);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/product/update/{id}", "12")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.view().name("editProduct"))
                ;

        MvcResult mvcResult  = result.andReturn();
        Product actualProduct = (Product) mvcResult.getRequest().getAttribute("product");
        verify(productService, times(1)).getProductById(12L);
        assertThat(actualProduct).isEqualTo(expectedProduct);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/product/update/12");
    }

    @Test
    public void shouldSendParameterEditProductToService_ByModel() throws Exception {
        doNothing().when(productService).editProduct(any(Product.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/product/update")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER))
                .param("id","12")
                .param("productName","Milk")
                .param("description","easy")
                .param("productPrice","2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/getAllProducts"));

        MvcResult mvcResult  = result.andReturn();

        verify(productService, times(1)).editProduct(any(Product.class));
        assertThat(mvcResult.getRequest().getParameterMap().get("id")[0]).isEqualTo("12");
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/product/update");
    }
}