package com.example.demo.controller;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.service.CustomerService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "admin/getAllCustomers", method = RequestMethod.GET)
    public String listCustomers (Model uiModel){
        List<Customer> customersList = customerService.getAllCustomers();
        uiModel.addAttribute("Customers", customersList);
        return "listCustomer";
    }

    @RequestMapping(value = "admin/getCustomer/{customerId}")
    public ModelAndView getCustomerById(@PathVariable(value = "customerId") Long id) {
        Customer customer = customerService.getCustomerById(id);
        return new ModelAndView("customerPage", "customerEntity", customer);
    }


    @RequestMapping(value = "admin/customer/update/{id}")
    public ModelAndView editCustomerForm(@PathVariable(value = "id") Long id) {
        Customer customer = customerService.getCustomerById(id);
        return new ModelAndView("editCustomer", "Customer", customer);
    }

    @RequestMapping(value = "admin/customer/update", method = RequestMethod.POST)
    public String editStatusCustomer(@ModelAttribute(value = "Customer") Customer customer) {
        userService.updateUserStatus(customer.getUser());
        return "redirect:/admin/getAllCustomers";
    }

    //////////////////////////////////////////////////////////////////
    @RequestMapping(value = "admin/customer/delete/{customerId}")
    public String deleteCustomer(@PathVariable(value = "customerId") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/getAllCustomers";
    }
    ///////////////////////////////////////////////////////////////////


}
