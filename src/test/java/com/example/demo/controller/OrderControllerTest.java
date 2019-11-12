package com.example.demo.controller;

import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.OrderService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class OrderControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter springSecurityFilterChain;

    private final String MANAGER = "manager@shop.ru";
    private final String USER = "customer@test.ru";

    private Order expectedOrder;
    private List<Order> expectedOrders;

    private static class TestDataStorage {
        Product product1 = new Product(12L, "Milk", "easy", 2, true);
        Product product2 = new Product(22L, "Book", "easy", 3, true);
        OrderDetail orderDetail1 = OrderDetail.builder().id(5L).product(product1).price(4).quantity(2).build();
        OrderDetail orderDetail2 = OrderDetail.builder().id(6L).product(product2).price(9).quantity(3).build();

        List<OrderDetail> orderDetailList = Arrays.asList(orderDetail1, orderDetail2);

        Order order = Order.builder().id(1L).totalPrice(13).totalQuantity(5).orderDetail(orderDetailList).statusOrder(Order.StatusOrder.NOT_READY).build();
        List<Order> orders = Collections.singletonList(order);
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain).dispatchOptions(true).build();
        expectedOrder = new TestDataStorage().order;
        expectedOrders = new TestDataStorage().orders;
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
        expectedOrder = null;
        expectedOrders = null;
    }

    @Test
    public void shouldReturnListOrdersOfCustomer() throws Exception {
        when(orderService.findAllByCustomerNotNullOrderByOrderCreateDateDesc()).thenReturn(expectedOrders);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAllOrders")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orders"))
                .andExpect(MockMvcResultMatchers.view().name("ordersList"));

        MvcResult mvcResult  = result.andReturn();
        List<Order> actualOrders = (List<Order>) mvcResult.getRequest().getAttribute("orders");
        verify(orderService, times(1)).findAllByCustomerNotNullOrderByOrderCreateDateDesc();
        assertThat(actualOrders).isEqualTo(expectedOrders);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/getAllOrders");
    }

    @Test
    public void shouldRequestOrderFromService_ByLongId() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(expectedOrder);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/order/{orderId}", "1")
                .with(SecurityRequestPostProcessors.userDetailsService(USER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("Order"))
                .andExpect(MockMvcResultMatchers.view().name("viewOrder"));

        MvcResult mvcResult  = result.andReturn();
        Order actualOrder = (Order) mvcResult.getRequest().getAttribute("Order");
        verify(orderService, times(1)).getOrderById(1L);
        assertThat(actualOrder).isEqualTo(expectedOrder);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/order/1");
    }

    @Test
    public void shouldRequestOrderFromServiceAndSendFormToEditOrderPage_ByLongId() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(expectedOrder);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/editOrder/{orderId}", "1")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("Order"))
                .andExpect(MockMvcResultMatchers.view().name("editOrder"))
                ;

        MvcResult mvcResult  = result.andReturn();
        Order actualOrder = (Order) mvcResult.getRequest().getAttribute("Order");
        verify(orderService, times(1)).getOrderById(1L);
        assertThat(actualOrder).isEqualTo(expectedOrder);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/editOrder/1");
    }

    @Test
    public void shouldSendParameterEditOrderToService_ByModel() throws Exception {
        doNothing().when(orderService).updateOrder(any(String.class), any(Order.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/editOrder")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER))
                .param("id","1")
        )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/getAllOrders"));

        MvcResult mvcResult  = result.andReturn();

        verify(orderService, times(1)).updateOrder(any(String.class), any(Order.class));
        assertThat(mvcResult.getRequest().getParameterMap().get("id")[0]).isEqualTo("1");
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/editOrder");
    }
}