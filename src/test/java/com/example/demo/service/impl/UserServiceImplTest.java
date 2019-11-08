package com.example.demo.service.impl;

import com.example.demo.dao.repository.RoleRepository;
import com.example.demo.dao.repository.UserRepository;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.model.UserDTO;
import com.example.demo.service.CustomerService;
import com.example.demo.service.UserService;
import com.google.common.collect.Lists;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Test
    public void shouldReturnListUsersWithNoContainsRole_ByEmail_LongId_3L() {

        Role role1 = Role.builder().id(1L).name("ROLE_ADMIN").build();
        Role role2 = Role.builder().id(2L).name("ROLE_MANAGER").build();
        Role role3 = Role.builder().id(3L).name("ROLE_CUSTOMER").build();
        Set<Role> roleSet1 = new HashSet<>();
        Set<Role> roleSet2 = new HashSet<>();
        roleSet1.add(role1);
        roleSet2.add(role2);
        User user1 = User.builder().id(1L).email("admin@shop.ru").roles(roleSet1).build();
        User user2 = User.builder().id(2L).email("manager1@shop.ru").roles(roleSet2).build();

        List<User> expectedUsers = Arrays.asList(user1, user2);

        doReturn(role3).when(roleRepository).getById(3L);
        when(userRepository.findAllByRolesIsNotContainingOrderByEmail(roleRepository.getById(3L))).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getUsersNotCustomer();

        verify(userRepository, times(1)).findAllByRolesIsNotContainingOrderByEmail(role3);
        verify(roleRepository, times(2)).getById(3L);
        assertNotNull(actualUsers);
        assertEquals(actualUsers.size(), 2);
        logger.info("Result: expectedUsers_size= " + expectedUsers.size() +
                ", actualUsers_size= " + actualUsers.size());
    }

    @Test
    public void shouldReturnUser_whenGetUserByIdIsCalled_LongId() {
        Role role2 = Role.builder().id(2L).name("ROLE_MANAGER").build();
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(role2);
        User expectedUser = User.builder().id(2L).email("manager1@shop.ru").roles(roleSet2).build();

        given(this.userRepository.getById(any())).willReturn(expectedUser);

        User actualUser = userService.getUserById(2L);

        verify(userRepository, times(1)).getById(2L);
        assertEquals((long) actualUser.getId(), 2L);
        assertThat(expectedUser).isEqualTo(actualUser);
        logger.info("Result: expected_user_id= " + expectedUser.getId() + ", actual_user_id= " + actualUser.getId());
    }

    @Test
    public void shouldReturnUser_whenGetByEmailIsCalled_StringEmail() {
        Role role2 = Role.builder().id(2L).name("ROLE_MANAGER").build();
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(role2);
        User expectedUser = User.builder().id(2L).email("manager1@shop.ru").roles(roleSet2).build();

        given(this.userRepository.getByEmail(any())).willReturn(expectedUser);

        User actualUser = userService.findUserByEmail("manager1@shop.ru");

        verify(userRepository, times(1)).getByEmail("manager1@shop.ru");
        assertEquals(actualUser.getEmail(), "manager1@shop.ru");
        assertThat(expectedUser).isEqualTo(actualUser);
        logger.info("Result: expected_user_id= " + expectedUser.getId() + ", actual_user_id= " + actualUser.getId());
    }

    @Test
    public void shouldReturnListAllRole() {
        Role role1 = Role.builder().id(1L).name("ROLE_ADMIN").build();
        Role role2 = Role.builder().id(2L).name("ROLE_MANAGER").build();
        Role role3 = Role.builder().id(3L).name("ROLE_CUSTOMER").build();
        List<Role> expectedRoleList = Lists.newArrayList(role1, role2, role3).stream()
                .sorted(Comparator.comparing(Role::getName)).collect(Collectors.toList());

        when(roleRepository.findAll()).thenReturn(expectedRoleList);

        List<Role> actualRoleList = userService.getAllRole();

        verify(roleRepository, times(1)).findAll();
        assertNotNull(actualRoleList);
        assertEquals(actualRoleList.size(), 3);
        assertThat(actualRoleList).isEqualTo(expectedRoleList);
        logger.info("Result: expectedProducts_size= " + actualRoleList.size() +
                ", actualProducts_size= " + expectedRoleList.size());
    }

    @Test
    public void shouldCallDeleteMethodOfUserRepository_whenDeleteUserIsCalled_ByLongId() {
        doNothing().when(userRepository).deleteById(5L);
        userService.deleteUser(5L);
        verify(userRepository, times(1)).deleteById(5L);
    }

    @Test
    public void shouldCreateUser_FromUserDTO_CallSaveMethodOfUserRepository() {
        Role role2 = Role.builder().id(2L).name("ROLE_MANAGER").build();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role2);
        UserDTO userDTO = UserDTO.builder().email("manager1@shop.ru").password("123").build();
        userDTO.setRoleSet(roleSet);

        User actualUser = User.builder().id(2L).email("manager1@shop.ru").roles(roleSet).build();

        when(roleRepository.getById(anyLong())).thenReturn(role2);
        when(customerService.updateUserSecurity(any(User.class))).thenReturn(actualUser);
        when(userRepository.save(any(User.class))).thenReturn(actualUser);

        userService.createUserFromUserDTO(userDTO);

        verify(roleRepository, times(1)).getById(2L);
        verify(customerService, times(1)).updateUserSecurity(actualUser);
        verify(userRepository, times(1)).save(actualUser);
        assertEquals(userDTO.getEmail(), actualUser.getEmail());
    }

    @Test
    public void shouldChangeUserStatusEnabled_toFalse() {
        Role role2 = Role.builder().id(2L).name("ROLE_MANAGER").build();
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(role2);
        User expectedUser = User.builder().id(2L).email("manager1@shop.ru").roles(roleSet2)
                .enabled(false).accountNonExpired(true).credentialsNonExpired(true).accountNonLocked(true)
                .build();

        doNothing().when(userRepository).updateUserStatus(
                expectedUser.getId(), expectedUser.isEnabled(), expectedUser.isAccountNonExpired(),
                expectedUser.isCredentialsNonExpired(), expectedUser.isAccountNonLocked()
        );

        userService.updateUserStatus(expectedUser);
        verify(userRepository, times(1)).updateUserStatus(
                expectedUser.getId(), expectedUser.isEnabled(), expectedUser.isAccountNonExpired(),
                expectedUser.isCredentialsNonExpired(), expectedUser.isAccountNonLocked()
        );
    }
}
