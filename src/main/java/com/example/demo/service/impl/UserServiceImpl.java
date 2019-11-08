package com.example.demo.service.impl;

import com.example.demo.dao.repository.RoleRepository;
import com.example.demo.dao.repository.UserRepository;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;

import com.example.demo.domain.model.UserDTO;
import com.example.demo.service.CustomerService;


import com.example.demo.service.UserService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersNotCustomer() {
        List<User> userList = userRepository.findAllByRolesIsNotContainingOrderByEmail(roleRepository.getById(3L));
        logger.info("GetUsersNotCustomer, userList_size= " + userList.size());
        return userList;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        User user = userRepository.getById(id);
        logger.info("GetUserById, user= " + user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        User user = userRepository.getByEmail(email);
        logger.info("FindUserByEmail, user= " + user);
        return user;
    }

    @Override
    public List<Role> getAllRole() {
        List<Role> roleList = Lists.newArrayList(roleRepository.findAll()).stream()
                .sorted(Comparator.comparing(Role::getName)).collect(Collectors.toList());
        logger.info("GetAllRole, userList_size= " + roleList.size());
        return roleList;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
        logger.info("Delete user_by_id, id= " + userId);
    }

    @Override
    public void createUserFromUserDTO(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        Set<Role> roles = user.getRoles();
        userDTO.getRoleSet()
                .forEach(o->roles.add(roleRepository.getById(o.getId())));
//                .forEach(o->roles.add(roleRepository.getById(o.getId()).findById(o.getId()).get()));
        user.setRoles(roles);

        User secureUser = customerService.updateUserSecurity(user);
        userRepository.save(secureUser);
        logger.info("Create user from userDTO, user= " + user);
    }

    @Override
    public void updateUserStatus(User user) {
        userRepository.updateUserStatus(
                user.getId(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked());
        logger.info("Update user_security_status, user= " + user);
    }

}