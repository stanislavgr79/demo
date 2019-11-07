package com.example.demo.controller;

import com.example.demo.domain.entity.shop.Order;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "admin/getAllOrders", method = RequestMethod.GET)
    public String listOrders (Model uiModel){
        List<Order> orders = orderService.findAllByCustomerNotNullOrderByOrderCreateDateDesc();
        uiModel.addAttribute("orders", orders);
        return "ordersList";
    }

    @RequestMapping("admin/editOrder/{orderId}")
    public @ResponseBody ModelAndView editOrder(@PathVariable(value = "orderId") Long id) {
        Order order = orderService.getOrderById(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("Order", order);
        mav.setViewName("editOrder");
        return mav;
    }

    @RequestMapping(value = "admin/editOrder/update", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute(value = "Order") Order order) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        String managerName = userDetails.getUsername();
        orderService.updateOrder(managerName, order);
        return "redirect:/admin/getAllOrders";
    }

    @RequestMapping("order/{orderId}")
    public @ResponseBody ModelAndView viewOrder(@PathVariable(value = "orderId") Long id) {
        Order order = orderService.getOrderById(id);
        return new ModelAndView("viewOrder", "Order", order);
    }

}
