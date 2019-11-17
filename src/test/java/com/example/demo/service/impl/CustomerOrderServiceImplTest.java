package com.example.demo.service.impl;

import com.example.demo.dao.CustomerOrderRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.CustomerOrder;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.service.CustomerOrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerOrderServiceImplTest {

    @MockBean
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerOrderService customerOrderService;

    private Order order;
    private Customer customer;
    private CustomerOrder expectedСustomerOrder;
    private CustomerOrder actualСustomerOrder;

    private static class TestDataStorage{
        Customer customer = Customer.builder().id(1L).build();
        Order order = Order.builder().id(1L).build();
        CustomerOrder expectedСustomerOrder = CustomerOrder.builder().id(1L).order(order).customer(customer).build();
        CustomerOrder actualСustomerOrder = CustomerOrder.builder().id(1L).order(order).customer(customer).build();
    }

    @Before
    public void setUp() {
        order = new TestDataStorage().order;
        customer = new TestDataStorage().customer;
        expectedСustomerOrder = new TestDataStorage().expectedСustomerOrder;
        actualСustomerOrder = new TestDataStorage().actualСustomerOrder;
    }

    @After
    public void tearDown() {
        order = null;
        customer = null;
        expectedСustomerOrder = null;
        actualСustomerOrder = null;
    }

    @Test
    public void shouldSaveCustomerOrder_CallSaveMethodOfCustomerOrderRepository() {
        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(actualСustomerOrder);

        customerOrderService.saveCustomerOrder(expectedСustomerOrder);

        verify(customerOrderRepository, times(1)).save(expectedСustomerOrder);
        assertThat(actualСustomerOrder).isEqualTo(expectedСustomerOrder);
    }

    @Test
    public void shouldCreateCustomerOrder_ByCustomerAndOrder() {
        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(expectedСustomerOrder);
        customerOrderService.createCustomerOrderByCustomerAndOrder(customer, order);
        verify(customerOrderRepository, times(1)).save(any(CustomerOrder.class));
    }

}