package com.example.demo.service;

import com.example.demo.domain.entity.person.User;
//import com.example.demo.security.FieldValueExists;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User findUserByEmail(String email);

    void createUser(User user);

    void deleteUser(Long userId);

    List<String> rolesByUserId(Long id);

    void updateUserStatus(User user);
}
