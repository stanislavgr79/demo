package com.example.demo.controller;

import com.example.demo.security.RegistrationForm;
import com.example.demo.security.RegistrationValidator;
import com.example.demo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RegistrationValidator registrationValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(registrationValidator);
    }

    @RequestMapping(value = "user/registration", method = RequestMethod.GET)
    public ModelAndView getRegistrationForm() {
        RegistrationForm registrationForm = new RegistrationForm();
    logger.info("ModelAndView take New RegistrationForm");
        return new ModelAndView("register", "registerForm", registrationForm);
    }

    @RequestMapping(value = "user/registration", method = RequestMethod.POST)
    public String registerCustomer(@ModelAttribute(value = "registerForm")
                                   @Valid @RequestBody RegistrationForm registrationForm,
                                   BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("registerForm", registrationForm);
            return "register";
        }
        logger.info("RegistrationForm send to createCustomerMethod, registrationForm= " + registrationForm);
        customerService.createCustomer(registrationForm);
        model.addAttribute("registrationSuccess", "Registered Successfully. You can login.");
        return "login";
    }
}
