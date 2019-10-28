package com.example.demo.service.impl;


import com.example.demo.dao.repository.UserRepository;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.service.UserService;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // for admin
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
       return userRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> rolesByUserId(Long id) {
        return  userRepository.findById(id).get()
                .getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    // check exists email
//    @Override
//    @Transactional(readOnly = true)
//    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
//        Assert.notNull(fieldName);
//        if (!fieldName.equals("email")) {
//            throw new UnsupportedOperationException("Field name not supported");
//        }
//        if (value == null) {
//            return false;
//        }
//        return userRepository.existsByEmail(value.toString());
//    }
}