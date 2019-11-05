package com.example.demo.controller;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = { "index", "home"}, method = RequestMethod.GET)
    public String doIndex() {

        logger.trace("This is a trace log example");
        logger.info("This is an info log example");
        logger.debug("This is a debug log example");
        logger.error("This is an error log example");
        logger.warn("This is a warn log example");

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

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        String email = auth.getName();
        Customer customer = customerService.getCustomerByEmail(userDetails.getUsername());

        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
//        System.out.println(userDetails.isEnabled());

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("customerDetail", customer);

        return "accountInfo";
    }
}
