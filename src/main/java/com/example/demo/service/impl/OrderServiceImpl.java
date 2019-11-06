package com.example.demo.service.impl;

import com.example.demo.dao.repository.OrderRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetail> getListOrderDetailByOrderId(Long orderId) {
        return orderDetailService.findOrderDetailsByOrder_Id(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.getOrdersByCustomerNotNull();
//        List<String> sortedList = list.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public void createOrderFromBasket(Customer customer, Basket basket) {

        Order order = new Order();
        order.setOrderCreateDate(new Date());
        order.setOrderModifyDate(order.getOrderCreateDate());
        order.setStatusOrder(Order.StatusOrder.NOT_READY);

        updateInfoQuantityAndPriceInOrderFromBasket(order, basket);
        order.setCustomer(customer);
        saveOrder(order);

        updateOrderBySaveOrderDetailFromBasket(order, basket);
        customer.addOrder(order);

        customerService.updateCustomer(customer);
        customerOrderService.createCustomerOrderByCustomerAndOrder(customer, order);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllByCustomerNotNullOrderByOrderCreateDateDesc() {
        return orderRepository.findAllByCustomerNotNullOrderByOrderCreateDateDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllByCustomer_IdOrderByOrderCreateDateDesc(Long id) {
        return orderRepository.findAllByCustomer_IdOrderByOrderCreateDateDesc(id);
    }

    @Override
    public void updateOrder(String managerName, Order order) {
        User user = userService.findUserByEmail(managerName);

        int status;
        switch (order.getStatusOrder()) {
            case NOT_READY: status = 0; break;
            case READY: status = 1; break;
            default: return;
        }
        orderRepository.updateOrder(
                order.getId(),
                new Date(),
                status,
                user.getEmail()
                );

    }

    void updateOrderBySaveOrderDetailFromBasket(Order order, Basket basket){

        List<OrderDetail> orderDetails = order.getOrderDetail();

        List<OrderDetailDTO> basketOrderDetail = basket.getOrderDetail();

        for (OrderDetailDTO el: basketOrderDetail){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(productService.getProductById(el.getProduct().getId()));
            orderDetail.setPrice(el.getPrice());
            orderDetail.setQuantity(el.getQuantity());
            orderDetail.setOrder(order);

            orderDetails.add(orderDetail);
            orderDetailService.saveOrderDetail(orderDetail);
        }
    }

    void updateInfoQuantityAndPriceInOrderFromBasket(Order order, Basket basket){
        order.setTotalQuantity(basket.getTotalQuantity());
        order.setTotalPrice(basket.getTotalPrice());
    }


}
