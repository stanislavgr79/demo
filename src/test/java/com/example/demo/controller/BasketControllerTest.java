package com.example.demo.controller;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import com.example.demo.service.BasketService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
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
public class BasketControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MockHttpSession session;

    @MockBean
    private OrderService orderService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private BasketService basketService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter springSecurityFilterChain;

    private final String USER = "customer@test.ru";

    private Basket expectedBasket;
    private Basket sessionBasket;
    private Customer expectedCustomer;

    private static class TestDataStorage {
        Product product1 = new Product( 2L, "Milk", "easy", 10.1, true);
        Product product2 = new Product( 5L, "Meat", "easy", 5.2, true);
        OrderDetailDTO orderDetailDTO1 = OrderDetailDTO.builder().product(product1).quantity(2).price(20.2).build();
        OrderDetailDTO orderDetailDTO2 = OrderDetailDTO.builder().product(product2).quantity(3).price(15.6).build();
        List<OrderDetailDTO> orderDetailDTOList = Arrays.asList(orderDetailDTO1, orderDetailDTO2);
        List<OrderDetailDTO> orderDetailDTOListSession = Arrays.asList(orderDetailDTO1);
        Basket expectedBasket = Basket.builder().idCustomer(5L).totalPrice(35.8).totalQuantity(5).orderDetail(orderDetailDTOList).build();
        Basket sessionBasket = Basket.builder().idCustomer(5L).totalPrice(20.2).totalQuantity(2).orderDetail(orderDetailDTOListSession).build();
        Customer expectedCustomer = Customer.builder().id(5L).user(User.builder().id(5L).email("customer@test.ru").build()).build();

    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain).dispatchOptions(true).build();
        expectedBasket = new TestDataStorage().expectedBasket;
        sessionBasket = new TestDataStorage().sessionBasket;
        expectedCustomer = new TestDataStorage().expectedCustomer;
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
        expectedBasket = null;
        sessionBasket = null;
        session.clearAttributes();
    }

    @Test
    public void shouldRedirectToLoginPageIfUserDontLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/basket/getCurrentBasket"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void shouldSendBasketToBasketPage_IfBasketDidNotWasInSession() throws Exception {
        when(customerService.getCustomerByEmail(any(String.class))).thenReturn(expectedCustomer);
        Basket temp = null;
        when(session.getAttribute(any(String.class))).thenReturn(temp);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/basket/getCurrentBasket")
                .with(SecurityRequestPostProcessors.userDetailsService(USER)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("Basket"))
                .andExpect(MockMvcResultMatchers.view().name("basket"))
                ;

        MvcResult mvcResult  = result.andReturn();
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/basket/getCurrentBasket");
    }

    @Test
    public void shouldSendBasketToBasketPage_IfBasketWasInSession() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/basket/getCurrentBasket")
                .with(SecurityRequestPostProcessors.userDetailsService(USER))
                .sessionAttr("basket", expectedBasket))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("Basket"))
                .andExpect(MockMvcResultMatchers.view().name("basket"));

        MvcResult mvcResult  = result.andReturn();
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/basket/getCurrentBasket");
    }

    @Test
    public void shouldUpdateBasket() throws Exception {
        doNothing().when(basketService).updateInfoPriceAndQuantityInBasketSession(any(Basket.class), any(Basket.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/basket/getCurrentBasket?action1")
                        .with(SecurityRequestPostProcessors.userDetailsService(USER))
                        .sessionAttr("basket", sessionBasket)
                .flashAttr("Basket", expectedBasket))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/basket/getCurrentBasket"));

        MvcResult mvcResult  = result.andReturn();

        verify(basketService, times(1)).updateInfoPriceAndQuantityInBasketSession(any(Basket.class), any(Basket.class));
        assertThat(sessionBasket.getOrderDetail().size()).isEqualTo(expectedBasket.getOrderDetail().size());
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/basket/getCurrentBasket");
    }

    @Test
    public void shouldSendBasketAndCustomerToServiceCreateOrder() throws Exception {
        doNothing().when(basketService).updateInfoPriceAndQuantityInBasket(any(Basket.class));
        doNothing().when(orderService).createOrderFromBasket(any(Customer.class), any(Basket.class));
        when(customerService.getCustomerByEmail(any(String.class))).thenReturn(expectedCustomer);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/basket/getCurrentBasket?action2")
                .with(SecurityRequestPostProcessors.userDetailsService(USER))
                .sessionAttr("basket", sessionBasket)
                .flashAttr("Basket", expectedBasket)
        )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/accountInfo"));

        MvcResult mvcResult  = result.andReturn();

        verify(basketService, times(1)).updateInfoPriceAndQuantityInBasket(any(Basket.class));
        verify(orderService, times(1)).createOrderFromBasket(any(Customer.class), any(Basket.class));
        verify(customerService, times(1)).getCustomerByEmail(any(String.class));
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/basket/getCurrentBasket");
    }

    @Test
    public void shouldAddOrderDetailToBasketWhenBasketWasInSession() throws Exception {
        doNothing().when(basketService).addProductToBasket(expectedBasket, 2L);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/basket/add/{productId}", "2")
                .sessionAttr("basket", expectedBasket)
                .with(SecurityRequestPostProcessors.userDetailsService(USER)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        MvcResult mvcResult  = result.andReturn();
        verify(basketService, times(1)).addProductToBasket(expectedBasket, 2L);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/basket/add/2");
    }

    @Test
    public void shouldAddOrderDetailToBasketWhenBasketWasNotBeInSession() throws Exception {
        doNothing().when(basketService).addProductToBasket(any(Basket.class), anyLong());
        when(customerService.getCustomerByEmail(any(String.class))).thenReturn(expectedCustomer);
        Basket temp = null;
        when(session.getAttribute(any(String.class))).thenReturn(temp);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/basket/add/{productId}", "2")
                .with(SecurityRequestPostProcessors.userDetailsService(USER)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        MvcResult mvcResult  = result.andReturn();
        verify(basketService, times(1)).addProductToBasket(any(Basket.class), anyLong());
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/basket/add/2");
    }

    @Test
    public void shouldRemoveOrderDetailFromBasket() throws Exception {
        doNothing().when(basketService).removeOrderDetailByProductId(expectedBasket, 2L);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/basket/removeOrderDetail/{orderDetailByProductId}", "2")
                .sessionAttr("basket", expectedBasket)
                .with(SecurityRequestPostProcessors.userDetailsService(USER)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/basket/getCurrentBasket"));

        MvcResult mvcResult  = result.andReturn();
        verify(basketService, times(1)).removeOrderDetailByProductId(expectedBasket, 2L);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/basket/removeOrderDetail/2");
    }

    @Test
    public void shouldRemoveAllOrderDetailsFromBasket() throws Exception {
        doNothing().when(basketService).clearOrderDetailList(expectedBasket);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/basket/removeAllDetails")
                .sessionAttr("basket", expectedBasket)
                .with(SecurityRequestPostProcessors.userDetailsService(USER))
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/basket/getCurrentBasket"));

        MvcResult mvcResult  = result.andReturn();
        verify(basketService, times(1)).clearOrderDetailList(expectedBasket);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/basket/removeAllDetails");
    }
}