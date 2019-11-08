package com.example.demo.service.impl;

import com.example.demo.dao.repository.CustomerOrderRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.CustomerOrder;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.service.CustomerOrderService;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImplTest.class);

    @MockBean
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerOrderService customerOrderService;


    @Test
    public void shouldSaveCustomerOrder_CallSaveMethodOfCustomerOrderRepository() {

        Customer customer = Customer.builder().id(1L).build();
        Order order = Order.builder().id(1L).build();

        CustomerOrder expectedСustomerOrder = new CustomerOrder();
        expectedСustomerOrder.setId(1L);
        expectedСustomerOrder.setOrder(order);
        expectedСustomerOrder.setCustomer(customer);

        CustomerOrder actualСustomerOrder = new CustomerOrder();
        actualСustomerOrder.setId(1L);
        actualСustomerOrder.setOrder(order);
        actualСustomerOrder.setCustomer(customer);

        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(actualСustomerOrder);

        customerOrderService.saveCustomerOrder(expectedСustomerOrder);

        verify(customerOrderRepository, times(1)).save(expectedСustomerOrder);
        assertThat(actualСustomerOrder).isEqualTo(expectedСustomerOrder);
        logger.info("OrderDetail save successfully: " + actualСustomerOrder);
    }

    @Test
    public void shouldCreateCustomerOrder_ByCustomerAndOrder() {
        Customer customer = Customer.builder().id(1L).build();
        Order order = Order.builder().id(1L).build();

        CustomerOrder expectedСustomerOrder = new CustomerOrder();
        expectedСustomerOrder.setOrder(order);
        expectedСustomerOrder.setCustomer(customer);

        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(expectedСustomerOrder);
        customerOrderService.createCustomerOrderByCustomerAndOrder(customer, order);
        verify(customerOrderRepository, times(1)).save(expectedСustomerOrder);
    }

}