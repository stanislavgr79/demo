package com.example.demo.controller;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.service.CustomerService;
import com.example.demo.service.UserService;
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

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter springSecurityFilterChain;

    private final String MANAGER = "manager@shop.ru";

    private Customer expectedCustomer;
    private List<Customer> expectedCustomers;

    private static class TestDataStorage {
        Role role = Role.builder().id(3L).name("ROLE_USER").build();
        Set<Role> roleSet = new HashSet<>(Collections.singletonList(role));

        User expectedUser = User.builder().id(12L).email("ivanov@mail.ru").password("encryptPassword").roles(roleSet).build();
        Customer expectedCustomer = Customer.builder().id(12L).user(expectedUser).build();
        List<Customer> expectedCustomerList = Collections.singletonList(expectedCustomer);
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain).dispatchOptions(true).build();
        expectedCustomer = new TestDataStorage().expectedCustomer;
        expectedCustomers = new TestDataStorage().expectedCustomerList;
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
        expectedCustomer = null;
        expectedCustomers = null;
    }

    @Test
    public void shouldReturnListCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(expectedCustomers);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAllCustomers")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("Customers"))
                .andExpect(MockMvcResultMatchers.view().name("listCustomer"))
                ;

        MvcResult mvcResult  = result.andReturn();
        List<Customer> actualCustomers = (List<Customer>) mvcResult.getRequest().getAttribute("Customers");
        verify(customerService, times(1)).getAllCustomers();
        assertThat(actualCustomers).isEqualTo(expectedCustomers);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/getAllCustomers");
    }

    @Test
    public void shouldRequestCustomerFromService_ByLongId() throws Exception {
        when(customerService.getCustomerById(12L)).thenReturn(expectedCustomer);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/getCustomer/{customerId}", "12")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.view().name("viewCustomer"))
                ;

        MvcResult mvcResult  = result.andReturn();
        Customer actualCustomer = (Customer) mvcResult.getRequest().getAttribute("customer");
        verify(customerService, times(1)).getCustomerById(12L);
        assertThat(actualCustomer).isEqualTo(expectedCustomer);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/getCustomer/12");
    }

    @Test
    public void shouldRequestCustomerFromServiceAndSendFormToEditCustomerPage_ByLongId() throws Exception {
        when(customerService.getCustomerById(12L)).thenReturn(expectedCustomer);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/customer/update/{id}", "12")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("Customer"))
                .andExpect(MockMvcResultMatchers.view().name("editCustomer"))
                ;

        MvcResult mvcResult  = result.andReturn();
        Customer actualCustomer = (Customer) mvcResult.getRequest().getAttribute("Customer");
        verify(customerService, times(1)).getCustomerById(12L);
        assertThat(actualCustomer).isEqualTo(expectedCustomer);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/customer/update/12");
    }

    @Test
    public void shouldSendParameterEditCustomerToService_ByModel() throws Exception {
        doNothing().when(userService).updateUserStatus(any(User.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/customer/update")
                .with(SecurityRequestPostProcessors.userDetailsService(MANAGER))
                .param("id","12")
                .param("firstName","Ivanov")
                .param("lastName","Andrey")
                .param("user.id", "12")
        )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/getAllCustomers"));

        MvcResult mvcResult  = result.andReturn();

        verify(userService, times(1)).updateUserStatus(any(User.class));
        assertThat(mvcResult.getRequest().getParameterMap().get("id")[0]).isEqualTo("12");
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/admin/customer/update");
    }
}