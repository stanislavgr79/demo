package com.example.demo.controller;

import com.example.demo.domain.entity.person.Address;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.model.RegistrationForm;
import com.example.demo.domain.model.RegistrationValidator;
import com.example.demo.service.CustomerService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationValidator registrationValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(registrationValidator);
    }

    @RequestMapping(value = "user/registration", method = RequestMethod.GET)
    public ModelAndView  getRegistrationForm() {
        RegistrationForm registrationForm = new RegistrationForm();

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

        Customer customer = new Customer();
        User user = new User();
        Address address = new Address();
        customer.setAddress(address);
        customer.setUser(user);

        customer.setFirstName(registrationForm.getFirstName());
        customer.setLastName(registrationForm.getLastName());
        address.setCountry(registrationForm.getCountry());
        address.setCity(registrationForm.getCity());
        address.setStreet(registrationForm.getStreet());
        address.setFlat(registrationForm.getFlat());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(registrationForm.getPassword());

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
    public String editStatusCustomer(@ModelAttribute(value = "customer") Customer customer) {
        userService.updateUserStatus(customer.getUser());
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
