package com.example.demo.service.impl;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.model.UserDTO;
import com.example.demo.service.CustomerService;
import com.example.demo.service.UserService;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
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

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    private List<Role> expectedRoleList;
    private User expectedUser;
    private UserDTO userDTO;
    private List<User> expectedUsers;
    private Role roleManager;
    private Role roleUser;

    private static class TestDataStorage{
        Role role1 = Role.builder().id(1L).name("ROLE_ADMIN").build();
        Role role2 = Role.builder().id(2L).name("ROLE_MANAGER").build();
        Role role3 = Role.builder().id(3L).name("ROLE_USER").build();
        List<Role> expectedRoleList = Lists.newArrayList(role1, role2, role3).stream()
                .sorted(Comparator.comparing(Role::getName)).collect(Collectors.toList());

        Set<Role> roleSet = new HashSet<>(Collections.singletonList(role2));
        UserDTO userDTO = UserDTO.builder().email("manager1@shop.ru").password("123").roleSet(roleSet).build();
        Set<Role> roleSetOfAdmin = new HashSet<>(Collections.singletonList(role1));
        User admin = User.builder().id(1L).email("admin@shop.ru").roles(roleSetOfAdmin).build();
        User expectedUser = User.builder().id(2L).email("manager1@shop.ru").roles(roleSet)
                .enabled(false).accountNonExpired(true).credentialsNonExpired(true).accountNonLocked(true)
                .build();
        List<User> expectedUsers = Arrays.asList(admin, expectedUser);
    }

    @Before
    public void setUp() {
        expectedUsers = new TestDataStorage().expectedUsers;
        expectedRoleList = new TestDataStorage().expectedRoleList;
        expectedUser = new TestDataStorage().expectedUser;
        userDTO = new TestDataStorage().userDTO;
        roleManager = new TestDataStorage().role2;
        roleUser = new TestDataStorage().role3;
    }

    @After
    public void tearDown() {
        expectedRoleList = null;
        expectedUsers = null;
        expectedUser = null;
        userDTO = null;
        roleManager = null;
        roleUser = null;
    }

    @Test
    public void shouldReturnListUsersWithNoContainsRole_ByEmail_LongId_3L() {
        doReturn(roleUser).when(roleRepository).getById(3L);
        when(userRepository.findAllByRolesIsNotContainingOrderByEmail(roleRepository.getById(3L))).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getUsersNotCustomer();

        verify(userRepository, times(1)).findAllByRolesIsNotContainingOrderByEmail(roleUser);
        verify(roleRepository, times(2)).getById(3L);
        assertNotNull(actualUsers);
        assertEquals(actualUsers.size(), 2);
    }

    @Test
    public void shouldReturnUser_whenGetUserByIdIsCalled_LongId() {
        given(this.userRepository.getById(any())).willReturn(expectedUser);

        User actualUser = userService.getUserById(2L);

        verify(userRepository, times(1)).getById(2L);
        assertEquals((long) actualUser.getId(), 2L);
        assertThat(expectedUser).isEqualTo(actualUser);
    }

    @Test
    public void shouldReturnUser_whenGetByEmailIsCalled_StringEmail() {
        given(this.userRepository.getByEmail(any())).willReturn(expectedUser);

        User actualUser = userService.findUserByEmail("manager1@shop.ru");

        verify(userRepository, times(1)).getByEmail("manager1@shop.ru");
        assertEquals(actualUser.getEmail(), "manager1@shop.ru");
        assertThat(expectedUser).isEqualTo(actualUser);
    }

    @Test
    public void shouldReturnListAllRole() {
        when(roleRepository.findAll()).thenReturn(expectedRoleList);

        List<Role> actualRoleList = userService.getAllRole();

        verify(roleRepository, times(1)).findAll();
        assertNotNull(actualRoleList);
        assertEquals(actualRoleList.size(), 3);
        assertThat(actualRoleList).isEqualTo(expectedRoleList);
    }

    @Test
    public void shouldCallDeleteMethodOfUserRepository_whenDeleteUserIsCalled_ByLongId() {
        doNothing().when(userRepository).deleteById(5L);
        userService.deleteUser(5L);
        verify(userRepository, times(1)).deleteById(5L);
    }

    @Test
    public void shouldCreateUser_FromUserDTO_CallSaveMethodOfUserRepository() {
        when(roleRepository.getById(anyLong())).thenReturn(roleManager);
        when(customerService.updateUserSecurity(any(User.class))).thenReturn(expectedUser);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        userService.createUserFromUserDTO(userDTO);

        verify(roleRepository, times(1)).getById(2L);
        verify(customerService, times(1)).updateUserSecurity(expectedUser);
        verify(userRepository, times(1)).save(expectedUser);
        assertEquals(userDTO.getEmail(), expectedUser.getEmail());
    }

    @Test
    public void shouldChangeUserStatusEnabled_toFalse() {
        doNothing().when(userRepository).updateUserStatus(
                expectedUser.getId(), expectedUser.isEnabled(), expectedUser.isAccountNonExpired(),
                expectedUser.isCredentialsNonExpired(), expectedUser.isAccountNonLocked());

        userService.updateUserStatus(expectedUser);
        verify(userRepository, times(1)).updateUserStatus(
                expectedUser.getId(), expectedUser.isEnabled(), expectedUser.isAccountNonExpired(),
                expectedUser.isCredentialsNonExpired(), expectedUser.isAccountNonLocked());
    }
}
