package com.example.demo.service.impl;

import com.example.demo.dao.repository.CustomerRepository;
import com.example.demo.dao.repository.RoleRepository;
import com.example.demo.domain.entity.person.Address;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.security.RegistrationForm;
import com.example.demo.service.CustomerService;
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

    @Override
    public void createCustomer(RegistrationForm registrationForm) {

        Customer customer = buildCustomerFromRegistrationForm(registrationForm);

        User user = customer.getUser();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = user.getRoles();
        roles.add(roleRepository.findById(3L).get());
        user.setRoles(roles);
        customer.setUser(user);

        customerRepository.save(customer);

        logger.info("Customer save successfully, Customer="+ customer);
    }

    @Override
    public User updateUserSecurity(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public Customer buildCustomerFromRegistrationForm(RegistrationForm registrationForm) {

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

        return customer;
    }

}
