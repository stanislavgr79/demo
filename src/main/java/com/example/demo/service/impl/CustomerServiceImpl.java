package com.example.demo.service.impl;

import com.example.demo.dao.repository.CustomerRepository;
import com.example.demo.dao.repository.RoleRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return Lists.newArrayList(customerRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public void createCustomer(Customer customer) {
        Order order = new Order();
        customer.setOrder(order);
        User user = customer.getUser();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = user.getRoles();
        roles.add(roleRepository.findById(1L).get());
        user.setRoles(roles);
        customer.setUser(user);
        customerRepository.save(customer);
    }

    @Override
    public void saveOrderAndCreateNewOrder(Customer customer) {
        Order order = customer.getOrder();
        customer.getOrdersList().add(order);
        Order newOrder = new Order();
        customer.setOrder(newOrder);
        customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerByEmail(String email) {
        return customerRepository.getByUser_Email(email);
    }


    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }


}
