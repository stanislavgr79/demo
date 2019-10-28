package com.example.demo.controller;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping(value = "order/getCurrentOrder", method = RequestMethod.GET)
    public ModelAndView getCurrentOrder(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//        if (userDetail == null) {
//            return "redirect:/user/registration";
//        }
        Customer customer = customerService.getCustomerByEmail(email);
        Order order = customer.getOrder();
//        List<OrderDetail> orderDetailList = order.getOrderDetail();
//        model.addAttribute("order", order);
//        model.addAttribute("orderDetail", orderDetailList);
//        return "order";
        return new ModelAndView("order", "order", order);
    }


    @RequestMapping(value = "order/getCurrentOrder", method = RequestMethod.POST)
    public String updateOrder(@Valid @ModelAttribute(value = "order") Order order) {

        System.out.println(order.getId() + " id order");
        System.out.println(order.getOrderDetail().size() + " detail");
        System.out.println(order.getCustomer().getId() + " id customer");

        List<OrderDetail> list = order.getOrderDetail();

        for (OrderDetail orderDetail: list){
            System.out.println(orderDetail.getId() + " this is orderdetail id");
//            orderDetailService.saveOrderDetail(orderDetail);
        }

//        orderService.updateOrder(order);
        return "redirect:/order/getCurrentOrder";
    }


    @RequestMapping("order/{orderId}")
    public @ResponseBody
    Order getOrder(@PathVariable(value = "orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

}
