package com.example.demo.controller;

import com.example.demo.domain.entity.shop.Order;
import com.example.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "admin/getAllOrders", method = RequestMethod.GET)
    public String listOrders (Model uiModel){
        List<Order> orders = orderService.findAllByCustomerNotNullOrderByOrderCreateDateDesc();
        logger.info("List<Order> send to model, listOrders_size= " + orders.size());
        uiModel.addAttribute("orders", orders);
        return "ordersList";
    }

    @RequestMapping("order/{orderId}")
    @Transactional
    public @ResponseBody ModelAndView viewOrder(@PathVariable(value = "orderId") Long id) {
        Order order = orderService.getOrderById(id);
        logger.info("Order send to ModelAnView, order= " + order);
        return new ModelAndView("viewOrder", "Order", order);
    }

    @RequestMapping("admin/editOrder/{orderId}")
    public @ResponseBody ModelAndView editOrder(@PathVariable(value = "orderId") Long id) {
        Order order = orderService.getOrderById(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("Order", order);
        mav.setViewName("editOrder");
        logger.info("Order send for edit to ModelAndView, order= " + order);
        return mav;
    }

    @RequestMapping(value = "admin/editOrder", method = RequestMethod.POST)
    public String editOrder(@ModelAttribute(value = "Order") Order order) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        String managerName = userDetails.getUsername();
        orderService.updateOrder(managerName, order);
        logger.info("Order update status by manager, order= " + order + " manager= " + managerName);
        return "redirect:/admin/getAllOrders";
    }
}
