package com.example.demo.controller;

import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.model.UserDTO;
import com.example.demo.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter springSecurityFilterChain;

    private final String ADMIN = "admin@shop.ru";

    private User expectedUser;
    private List<Role> expectedRoles;
    private List<User> expectedUsers;

    private static class TestDataStorage {
        Role role1 = Role.builder().id(1L).name("ROLE_ADMIN").build();
        Role role2 = Role.builder().id(2L).name("ROLE_MANAGER").build();
        Role role3 = Role.builder().id(3L).name("ROLE_USER").build();
        Set<Role> roleSet1 = new HashSet<>(Collections.singletonList(role1));
        Set<Role> roleSet2 = new HashSet<>(Collections.singletonList(role2));

        User user1 = User.builder().id(1L).email("admin@shop.ru").roles(roleSet1).build();
        User user2 = User.builder().id(2L).email("manager1@shop.ru").roles(roleSet2).build();
        List<User> expectedUsers = Arrays.asList(user1, user2);
        List<Role> expectedRoles = Arrays.asList(role1, role2, role3);
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain).dispatchOptions(true).build();
        expectedUser = new TestDataStorage().user2;
        expectedRoles = new TestDataStorage().expectedRoles;
        expectedUsers = new TestDataStorage().expectedUsers;
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
        expectedRoles = null;
        expectedUsers = null;
        expectedUser = null;
    }

    @Test
    public void shouldReturnListUsersNotCustomerAndAllRoles() throws Exception {
        when(userService.getUsersNotCustomer()).thenReturn(expectedUsers);
        when(userService.getAllRole()).thenReturn(expectedRoles);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/secure/getUsersNotCustomer")
                .with(SecurityRequestPostProcessors.userDetailsService(ADMIN)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("Roles"))
                .andExpect(MockMvcResultMatchers.view().name("usersList"));

        MvcResult mvcResult  = result.andReturn();
        List<User> actualUsers = (List<User>) mvcResult.getRequest().getAttribute("users");
        List<Role> actualRoles = (List<Role>) mvcResult.getRequest().getAttribute("Roles");

        verify(userService, times(1)).getUsersNotCustomer();
        verify(userService, times(1)).getAllRole();
        assertThat(actualUsers).isEqualTo(expectedUsers);
        assertThat(actualRoles).isEqualTo(expectedRoles);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/secure/getUsersNotCustomer");
    }

    @Test
    public void shouldSendNewUserDtoAndRolesToRegUserPage() throws Exception {
        when(userService.getAllRole()).thenReturn(expectedRoles);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/secure/createUser")
                .with(SecurityRequestPostProcessors.userDetailsService(ADMIN)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("UserDto"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("Roles"))
                .andExpect(MockMvcResultMatchers.view().name("regUser"));

        MvcResult mvcResult  = result.andReturn();
        List<Role> actualRoles = (List<Role>) mvcResult.getRequest().getAttribute("Roles");

        verify(userService, times(1)).getAllRole();
        assertThat(actualRoles).isEqualTo(expectedRoles);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/secure/createUser");
    }

    @Test
    public void shouldSendParameterNewUserToService_ByModel() throws Exception {
        doNothing().when(userService).createUserFromUserDTO(any(UserDTO.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/secure/createUser")
                .with(SecurityRequestPostProcessors.userDetailsService(ADMIN))
                .param("email","email@email.ru")
                .param("password","pass"))
//                .param("roleSet.id", "2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/secure/getUsersNotCustomer"));

        MvcResult mvcResult  = result.andReturn();
        verify(userService, times(1)).createUserFromUserDTO(any(UserDTO.class));
        assertThat(mvcResult.getRequest().getParameterMap().get("email")[0]).isEqualTo("email@email.ru");
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/secure/createUser");
    }

    @Test
    public void shouldSendUserIdNotCustomerToServiceForDelete_LongId() throws Exception {
        doNothing().when(userService).deleteUser(2L);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/secure/deleteUser/{userId}", "2")
                .with(SecurityRequestPostProcessors.userDetailsService(ADMIN)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/secure/getUsersNotCustomer"));

        MvcResult mvcResult  = result.andReturn();
        verify(userService, times(1)).deleteUser(2L);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/secure/deleteUser/2");
    }

    @Test
    public void shouldRequestUserFromServiceAndSendToEditUserPage_ByLongId() throws Exception {
        when(userService.getUserById(2L)).thenReturn(expectedUser);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/secure/editUser/{userId}", "2")
                .with(SecurityRequestPostProcessors.userDetailsService(ADMIN)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("editUser"));

        MvcResult mvcResult  = result.andReturn();
        User actualUser = (User) mvcResult.getRequest().getAttribute("user");
        verify(userService, times(1)).getUserById(2L);
        assertThat(actualUser).isEqualTo(expectedUser);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/secure/editUser/2");
    }

    @Test
    public void shouldSendParameterEditUserToService_ByModel() throws Exception {
        doNothing().when(userService).updateUserStatus(any(User.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/secure/editUser")
                .with(SecurityRequestPostProcessors.userDetailsService(ADMIN))
                .param("id","2")
                .param("email","manager1@shop.ru")
                .param("password","pass")
        )
                //                .param("roleSet.id", "2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/secure/getUsersNotCustomer"));

        MvcResult mvcResult  = result.andReturn();

        verify(userService, times(1)).updateUserStatus(any(User.class));
        assertThat(mvcResult.getRequest().getParameterMap().get("id")[0]).isEqualTo("2");
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/secure/editUser");

    }

}
