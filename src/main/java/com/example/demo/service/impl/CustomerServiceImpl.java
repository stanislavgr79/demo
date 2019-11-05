package com.example.demo.service.impl;

import com.example.demo.dao.repository.CustomerRepository;
import com.example.demo.dao.repository.RoleRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.service.CustomerService;
import com.example.demo.service.UserService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;



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
        User user = customer.getUser();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = user.getRoles();
        roles.add(roleRepository.findById(1L).get());
        user.setRoles(roles);
        customer.setUser(user);
        customerRepository.save(customer);

        logger.info("Customer save successfully, Customer="+ customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerByEmail(String email) {
        return customerRepository.getByUser_Email(email);
    }

    @Override
    public void deleteCustomer(Long id) {
//        Long idUser = customerRepository.findById(id).get().getUser().getId();
//        userService.deleteUser(idUser);
        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

}
