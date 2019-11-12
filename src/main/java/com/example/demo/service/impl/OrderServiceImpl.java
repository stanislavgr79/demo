package com.example.demo.service.impl;

import com.example.demo.dao.repository.OrderRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import com.example.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        Order order = orderRepository.getById(id);
        logger.info("Order find by id successfully, order= " + order);
        return order;
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
        logger.info("Order save successfully, order= " + order);
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
//        customerService.addOrderToCustomerOrderList(customer, order);
        customer.addOrder(order);
        logger.info("Order fill fields from Basket, order= " + order + " basket= " + basket);

        customerService.updateCustomer(customer);
        logger.info("Order save to customer, customer= " + customer + " order= " + order);
        customerOrderService.createCustomerOrderByCustomerAndOrder(customer, order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllByCustomerNotNullOrderByOrderCreateDateDesc() {
        List<Order> orderList = orderRepository.findAllByCustomerNotNullOrderByOrderCreateDateDesc();
        logger.info("FindAllOrder (OrderByOrderCreateDateDesc), orderList_size= " + orderList.size());
        return orderList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllByCustomer_IdOrderByOrderCreateDateDesc(Long id) {
        List<Order> orderList = orderRepository.findAllByCustomer_IdOrderByOrderCreateDateDesc(id);
        logger.info("FindAllOrder (ByCustomer_Id), customer_id= " + id +
                " orderList_size= " + orderList.size());
        return orderList;
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
        logger.info("Update order status and managerName, order =" + order.getId() +
                " status= " + order.getStatusOrder().getSTATUS() +
                " managerName= " + managerName);
    }

    public void updateOrderBySaveOrderDetailFromBasket(Order order, Basket basket){

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
        logger.info("Save orderDetail to order from basket," +
                " orderDetailBasket_size= " + basketOrderDetail.size() +
                " orderDetailSize after save in order_id =" + order.getId() +
                " size= " + orderDetails.size());
    }

    private void updateInfoQuantityAndPriceInOrderFromBasket(Order order, Basket basket){
        order.setTotalQuantity(basket.getTotalQuantity());
        order.setTotalPrice(basket.getTotalPrice());
        logger.info("UpdateInfoQuantityAndPriceInOrder successfully, order= " + order);
    }

}
