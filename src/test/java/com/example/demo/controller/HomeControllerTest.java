package com.example.demo.controller;

import com.example.demo.dao.RoleRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.shop.Order;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class HomeControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter springSecurityFilterChain;

    private final String ADMIN = "admin@shop.ru";
    private final String USER = "customer@test.ru";

    @Before
    public void setUp()  {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain).dispatchOptions(true).build();
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }


    @Test
    public void shouldRedirectToIndexPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldRedirectToLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .param("error","false"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void shouldRedirectRoleAdminToPageManagementUserOfCompany() throws Exception {
        Role role = Role.builder().id(3L).name("ROLE_USER").build();

        when(roleRepository.getById(3L)).thenReturn(role);

        mockMvc.perform(MockMvcRequestBuilders.get("/accountInfo")
                .with(SecurityRequestPostProcessors.userDetailsService(ADMIN)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/secure/getUsersNotCustomer"));

        verify(roleRepository, times(1)).getById(3L);
    }

    @Test
    public void shouldRedirectRoleUserToPageAccountInfo() throws Exception {
        Role role = Role.builder().id(3L).name("ROLE_USER").build();
        Customer expectedCustomer = Customer.builder().id(1L).build();
        Order order1 = Order.builder().id(1L).customer(expectedCustomer).build();
        Order order2 = Order.builder().id(2L).customer(expectedCustomer).build();
        List<Order> expectedOrders = Arrays.asList(order1, order2);

        when(roleRepository.getById(3L)).thenReturn(role);
        when(customerService.getCustomerByEmail(any(String.class))).thenReturn(expectedCustomer);
        when(orderService.findAllByCustomer_IdOrderByOrderCreateDateDesc(any(Long.class))).thenReturn(expectedOrders);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/accountInfo")
                .with(SecurityRequestPostProcessors.userDetailsService(USER)))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("userDetails"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("customerDetail"))
                .andExpect(MockMvcResultMatchers.view().name("accountInfo"));

        verify(roleRepository, times(1)).getById(3L);
        verify(customerService, times(1)).getCustomerByEmail(any(String.class));
        verify(orderService, times(1)).findAllByCustomer_IdOrderByOrderCreateDateDesc(any(Long.class));

        MvcResult mvcResult  = result.andReturn();
        List<Order> actualOrders = (List<Order>) mvcResult.getRequest().getAttribute("orders");
        Customer actualCustomer = (Customer) mvcResult.getRequest().getAttribute("customerDetail");

        assertThat(expectedOrders.get(0).getId()).isEqualTo(actualOrders.get(0).getId());
        assertThat(expectedCustomer.getId()).isEqualTo(actualCustomer.getId());
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/accountInfo");
    }
}