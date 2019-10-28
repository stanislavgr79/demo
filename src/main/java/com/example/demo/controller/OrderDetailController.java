package com.example.demo.controller;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class OrderDetailController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("order/add/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addOrderDetail(@PathVariable(value = "productId") Long productId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Customer customer = customerService.getCustomerByEmail(email);
        orderDetailService.addOrderDetail(customer, productId);
    }

    @RequestMapping("order/removeOrderDetail/{orderDetailId}")
    public String removeOrderDetail(@PathVariable(value = "orderDetailId") Long orderDetailId) {
        orderDetailService.removeOrderDetail(orderDetailId);
        return "redirect:/order/getCurrentOrder";
    }


    @RequestMapping("order/removeAllDetails/{orderId}")
    public String removeAllOrderDetails(@PathVariable(value = "orderId") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        orderDetailService.removeAllOrdersDetail(order);
        return "redirect:/order/getCurrentOrder";
    }
}

