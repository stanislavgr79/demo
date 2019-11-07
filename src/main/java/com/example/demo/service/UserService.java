package com.example.demo.service;

import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.model.UserDTO;
//import com.example.demo.security.FieldValueExists;

import java.util.List;


public interface UserService {

    List<User> getUsersNotCustomer();

    User getUserById(Long id);

    User findUserByEmail(String email);

    void createUserFromUserDTO(UserDTO userDTO);

    void deleteUser(Long userId);

    void updateUserStatus(User user);

    List<Role> getAllRole();
}
