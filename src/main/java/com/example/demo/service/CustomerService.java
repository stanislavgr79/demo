package com.example.demo.service;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.security.RegistrationForm;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    void createCustomer(RegistrationForm registrationForm);

    Customer getCustomerByEmail(String email);

    void updateCustomer(Customer customer);

    Customer buildCustomerFromRegistrationForm(RegistrationForm registrationForm);

    User updateUserSecurity(User user);

}
