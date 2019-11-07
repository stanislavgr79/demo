package com.example.demo.service.impl;

import com.example.demo.dao.repository.RoleRepository;
import com.example.demo.dao.repository.UserRepository;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;

import com.example.demo.domain.model.UserDTO;
import com.example.demo.service.CustomerService;


import com.example.demo.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersNotCustomer() {
        return userRepository.findAllByRolesIsNotContainingOrderByEmail(roleRepository.findById(3L).get());
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
    public List<Role> getAllRole() {
        return Lists.newArrayList(roleRepository.findAll()).stream()
                .sorted(Comparator.comparing(Role::getName)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void createUserFromUserDTO(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        Set<Role> roles = user.getRoles();
        userDTO.getRoleSet()
                .forEach(o->roles.add(roleRepository.findById(o.getId()).get()));
        user.setRoles(roles);

        User secureUser = customerService.updateUserSecurity(user);
        userRepository.save(secureUser);
    }

    @Override
    public void updateUserStatus(User user) {
        userRepository.updateUserStatus(
                user.getId(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked());
    }

}