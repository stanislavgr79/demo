package com.example.demo.controller;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = { "index", "home"}, method = RequestMethod.GET)
    public String doIndex() {

        logger.info("This is a index/home page");

        return "index";
    }

    @RequestMapping("login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null)
            model.addAttribute("error", "Invalid username and Password");
        if (logout != null){

            model.addAttribute("logout", "You have logged out successfully");
        }

        return "login";
    }

    @RequestMapping(value = { "accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        logger.info("Current user online in accountInfo.jsp = " + userDetails.getUsername());
        Customer customer = customerService.getCustomerByEmail(userDetails.getUsername());

        List<Order> orders =
                orderService.findAllByCustomer_IdOrderByOrderCreateDateDesc(customer.getId());


        model.addAttribute("userDetails", userDetails);
        model.addAttribute("orders", orders);
        model.addAttribute("customerDetail", customer);

        return "accountInfo";
    }
}
