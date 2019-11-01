package com.example.demo.controller;

import com.example.demo.domain.entity.person.Address;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.User;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "user/registration", method = RequestMethod.GET)
    public ModelAndView  getRegistrationForm() {
        Customer customer = new Customer();
        User user = new User();
        Address address = new Address();
        customer.setAddress(address);
        customer.setUser(user);
        return new ModelAndView("register", "customer", customer);
    }

    @RequestMapping(value = "user/registration", method = RequestMethod.POST)
    public String registerCustomer(@Valid @ModelAttribute(value = "customer") Customer customer, Model model,
                                   BindingResult result) {
        if (result.hasErrors())
            return "register";
        customerService.createCustomer(customer);
        model.addAttribute("registrationSuccess", "Registered Successfully. You can login.");
        return "login";
    }

    @RequestMapping(value = "admin/getAllCustomers", method = RequestMethod.GET)
    public String listCustomers (Model uiModel){
        List<Customer> customersList = customerService.getAllCustomers();
        uiModel.addAttribute("Customers", customersList);
        return "listCustomer";
    }

    @RequestMapping(value = "/saveCustomers", method = RequestMethod.POST)
    public void saveCustomers(@ModelAttribute("Customers") List<Customer> customerList) {
       for(Customer customer: customerList) {
           customerService.updateCustomer(customer);
       }
    }

    @RequestMapping(value = "admin/customer/update/{id}")
    public ModelAndView editCustomerForm(@PathVariable(value = "id") Long id) {
        Customer customer = customerService.getCustomerById(id);
        return new ModelAndView("editCustomer", "customer", customer);
    }

    @RequestMapping(value = "admin/customer/update", method = RequestMethod.POST)
    public String editCustomer(@ModelAttribute(value = "customer") Customer customer) {
        customerService.updateCustomer(customer);
        return "redirect:/admin/getAllCustomers";
    }

    @RequestMapping(value = "admin/getCustomer/{customerId}")
    public ModelAndView getCustomerById(@PathVariable(value = "customerId") Long id) {
        Customer customer = customerService.getCustomerById(id);
        return new ModelAndView("customerPage", "customerEntity", customer);
    }

    @RequestMapping(value = "admin/customer/delete/{customerId}")
    public String deleteCustomer(@PathVariable(value = "customerId") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/getAllCustomers";
    }

}
