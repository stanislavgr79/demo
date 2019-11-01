package com.example.demo.service;


import com.example.demo.domain.entity.person.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    void createCustomer(Customer customer);

    Customer getCustomerByEmail(String email);

    void deleteCustomer(Long id);

    void updateCustomer(Customer customer);

}
