package com.example.demo.service.impl;

import static org.junit.Assert.*;

import com.example.demo.dao.repository.OrderDetailRepository;
import com.example.demo.dao.repository.OrderRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import com.example.demo.service.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderDetailRepository orderDetailRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerOrderService customerOrderService;


    @Test
    public void shouldReturnOrder_ByOrderId() {
        Order expectedOrder = Order.builder().id(1L).build();

        given(orderRepository.getById(1L)).willReturn(expectedOrder);

        Order actualOrder = orderService.getOrderById(1L);

        verify(orderRepository, times(1)).getById(1L);
        assertThat(actualOrder.getId()).isEqualTo(expectedOrder.getId());
    }

    @Test
    public void shouldSaveOrder_CallSaveMethodOfOrderRepository() {
        Order expectedOrder = Order.builder().id(1L).build();
        Order actualOrder = Order.builder().id(1L).build();

        when(orderRepository.save(expectedOrder)).thenReturn(actualOrder);

        orderService.saveOrder(expectedOrder);

        verify(orderRepository, times(1)).save(expectedOrder);
        assertThat(actualOrder.getId()).isEqualTo(expectedOrder.getId());
    }

    @Ignore
    @Test
    public void createOrderFromBasket() {
        Customer expectedCustomer = Customer.builder()
                .id(12L).firstName("Ivanov").lastName("Andrey")
                .user(User.builder().id(2L).email("ivanov@mail.ru").password("123").build())
                .build();
        Basket basket = Basket.builder().totalQuantity(5).totalPrice(13).idCustomer(12L).build();
        List<OrderDetailDTO> basketOrderDetail = new ArrayList<>();
        Product product1 = new Product(12L, "Milk", "easy", 2, true);
        Product product2 = new Product(22L, "Book", "easy", 3, true);
        OrderDetailDTO orderDetailDTO1 = OrderDetailDTO.builder().product(product1).price(4).quantity(2).build();
        OrderDetailDTO orderDetailDTO2 = OrderDetailDTO.builder().product(product2).price(9).quantity(3).build();
        basketOrderDetail.add(orderDetailDTO1);
        basketOrderDetail.add(orderDetailDTO2);
        basket.setOrderDetail(basketOrderDetail);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail1 = OrderDetail.builder().product(product1).price(4).quantity(2).build();
        OrderDetail orderDetail2 = OrderDetail.builder().product(product2).price(9).quantity(3).build();
        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetail2);

        Order order = Order.builder().id(1L).totalPrice(13).totalQuantity(5).orderDetail(orderDetailList).customer(expectedCustomer).statusOrder(Order.StatusOrder.NOT_READY).build();
//=========================
        given(orderRepository.save(any(Order.class))).willReturn(order);
//        doNothing().when(customerService).addOrderToCustomerOrderList(any(Customer.class), any(Order.class));

        when(productService.getProductById(any(Long.class)))
                .thenReturn(product1).thenReturn(product2);
        given(orderDetailRepository.save(any(OrderDetail.class)))
                .willReturn(orderDetail1, orderDetail2);

        doNothing().when(customerService).updateCustomer(any(Customer.class));
        doNothing().when(customerOrderService).createCustomerOrderByCustomerAndOrder(any(Customer.class),any(Order.class));
//==========================
        this.orderService.createOrderFromBasket(expectedCustomer, basket);
//==========================
        verify(orderRepository, times(1)).save(any(Order.class));
//        verify(customerService, times(1)).addOrderToCustomerOrderList(any(Customer.class), any(Order.class));

        verify(productService, times(2)).getProductById(any(Long.class));
        verify(orderDetailRepository, times(2)).save(any(OrderDetail.class));

        verify(customerService, times(1)).updateCustomer(any(Customer.class));
        verify(customerOrderService, times(1)).createCustomerOrderByCustomerAndOrder(any(Customer.class),any(Order.class));

        assertThat(expectedCustomer.getOrdersList().stream().findFirst().get()).isEqualTo(order);
    }

    @Test
    public void shouldReturnListAllOrders_ByCustomerNotNull() {
        Order order1 = Order.builder().id(1L).customer(Customer.builder().id(1L).build()).build();
        Order order2 = Order.builder().id(2L).customer(Customer.builder().id(2L).build()).build();

        List<Order> expectedOrders = Arrays.asList(order1, order2);

        when(orderRepository.findAllByCustomerNotNullOrderByOrderCreateDateDesc()).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.findAllByCustomerNotNullOrderByOrderCreateDateDesc();

        verify(orderRepository, times(1)).findAllByCustomerNotNullOrderByOrderCreateDateDesc();
        assertNotNull(actualOrders);
        assertEquals(actualOrders.size(), expectedOrders.size());
        assertThat(actualOrders).isEqualTo(expectedOrders);
    }

    @Test
    public void shouldReturnListAllOrders_ByCustomerId() {
        Order order1 = Order.builder().id(1L).customer(Customer.builder().id(1L).build()).build();
        Order order2 = Order.builder().id(2L).customer(Customer.builder().id(1L).build()).build();

        List<Order> expectedOrders = Arrays.asList(order1, order2);

        when(orderRepository.findAllByCustomer_IdOrderByOrderCreateDateDesc(1L)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.findAllByCustomer_IdOrderByOrderCreateDateDesc(1L);

        verify(orderRepository, times(1)).findAllByCustomer_IdOrderByOrderCreateDateDesc(1L);
        assertNotNull(actualOrders);
        assertEquals(actualOrders.size(), expectedOrders.size());
        assertThat(actualOrders).isEqualTo(expectedOrders);
        assertThat(actualOrders.stream().filter(o-> o.getCustomer().getId().equals(1L)).count())
                .isEqualTo(expectedOrders.stream().filter(o-> o.getCustomer().getId().equals(1L)).count());
    }

    @Ignore
    @Test
    public void shouldUpdateOrderStatus_ByOrderAndEmailManager() {
        User actualUser = User.builder().id(2L).email("manager1@shop.ru").build();
        Order order = Order.builder().id(1L)
                .customer(Customer.builder().id(1L).build())
                .statusOrder(Order.StatusOrder.NOT_READY)
                .build();

        doReturn(actualUser).when(userService).findUserByEmail("manager1@shop.ru");
        doNothing().when(orderRepository).updateOrder(1L, new Date(), 0, "manager1@shop.ru");

        orderService.updateOrder("manager1@shop.ru", order);

        verify(userService, times(1)).findUserByEmail("manager1@shop.ru");
    }
}