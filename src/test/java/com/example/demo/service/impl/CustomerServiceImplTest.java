package com.example.demo.service.impl;

import static org.junit.Assert.*;

import com.example.demo.dao.repository.CustomerRepository;
import com.example.demo.dao.repository.RoleRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.security.RegistrationForm;
import com.example.demo.service.CustomerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceImplTest {

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private CustomerService customerService;

    private List<Customer> expectedCustomers;
    private Customer expectedCustomer;
    private Customer actualCustomer;
    private User expectedUser;

    private static class TestDataStorage{
        Customer expectedCustomer = Customer.builder().id(12L).user(User.builder().email("test@test.ru").build()).build();
        Customer actualCustomer = Customer.builder().id(12L).user(User.builder().email("test@test.ru").build()).build();
        List<Customer> expectedCustomers = Arrays.asList(expectedCustomer, actualCustomer);
        User expectedUser = User.builder().id(1L).password("123").build();
    }

    @Before
    public void setUp() {
        expectedCustomers = new TestDataStorage().expectedCustomers;
        expectedCustomer = new TestDataStorage().expectedCustomer;
        actualCustomer = new TestDataStorage().actualCustomer;
        expectedUser = new TestDataStorage().expectedUser;
    }

    @After
    public void tearDown() {
        expectedCustomers = null;
        expectedCustomer = null;
        actualCustomer = null;
        expectedUser = null;
    }

    @Test
    public void shouldReturnListAllCustomers_CallFindAllMethodOfCustomerRepository() {
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        List<Customer> actualCustomers = customerService.getAllCustomers();

        verify(customerRepository, times(1)).findAll();
        assertNotNull(actualCustomers);
        assertEquals(actualCustomers.size(), 2);
        assertThat(actualCustomers).isEqualTo(expectedCustomers);
    }

    @Test
    public void shouldReturnCustomer_ByCustomerId() {
        given(customerRepository.getById(12L)).willReturn(expectedCustomer);

        Customer actualCustomer = customerService.getCustomerById(12L);

        verify(customerRepository, times(1)).getById(12L);
        assertThat(actualCustomer.getId()).isEqualTo(expectedCustomer.getId());
    }

    @Test
    public void shouldReturnCustomer_ByUserEmail() {
        given(customerRepository.getByUser_Email("test@test.ru")).willReturn(expectedCustomer);

        Customer actualCustomer = customerService.getCustomerByEmail("test@test.ru");

        verify(customerRepository, times(1)).getByUser_Email("test@test.ru");
        assertThat(actualCustomer.getId()).isEqualTo(expectedCustomer.getId());
    }

    @Test
    public void shouldDeleteCustomer_ByCustomerId() {
        doNothing().when(customerRepository).deleteById(5L);
        customerService.deleteCustomer(5L);
        verify(customerRepository, times(1)).deleteById(5L);
    }

    @Test
    public void shouldUpdateCustomer_CallSaveMethodOfCustomerRepository() {
        given(customerRepository.save(expectedCustomer)).willReturn(actualCustomer);

        customerService.updateCustomer(expectedCustomer);

        verify(customerRepository, times(1)).save(expectedCustomer);
        assertThat(actualCustomer.getId()).isEqualTo(expectedCustomer.getId());
    }

    @Ignore
    @Test
    public void shouldCreateCustomerAddRoleCryptPasswordAndSave_ByRegistrationForm() {
        Role role = Role.builder().id(3L).name("ROLE_USER").build();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User actualUser = User.builder().email("ivanov@mail.ru").password("encryptPassword").roles(roleSet).build();
        Customer actualCustomer = Customer.builder().id(12L).user(actualUser).build();

        RegistrationForm registrationForm = RegistrationForm.builder()
                .password("123").email("ivanov@mail.ru").build();

        given(bCryptPasswordEncoder.encode("123")).willReturn("encryptPassword");
        given(roleRepository.getById(3L)).willReturn(role);
        given(customerRepository.save(any(Customer.class))).willReturn(actualCustomer);

        customerService.createCustomer(registrationForm);

        verify(bCryptPasswordEncoder, times(1)).encode("123");
        verify(roleRepository, times(1)).getById(3L);
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertThat(registrationForm.getEmail()).isEqualTo(actualCustomer.getUser().getEmail());
        assertThat(actualUser.getPassword()).isEqualTo(actualCustomer.getUser().getPassword());
        assertThat(actualUser.getRoles().contains(role)).isEqualTo(actualCustomer.getUser().getRoles().contains(role));
    }

    @Test
    public void shouldReturnUserAfterPasswordEncoder() {
        given(bCryptPasswordEncoder.encode(expectedUser.getPassword())).willReturn("encryptPassword");

        User actualUser = customerService.updateUserSecurity(expectedUser);

        verify(bCryptPasswordEncoder, times(1)).encode("123");
        assertThat(actualUser.getPassword()).isEqualTo("encryptPassword");
    }

    @Test
    public void shouldReturnCustomer_BuildByRegistrationForm() {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setFirstName("Ivanov");

        Customer actualCustomer = customerService.buildCustomerFromRegistrationForm(registrationForm);

        assertThat(actualCustomer.getFirstName()).isEqualTo(registrationForm.getFirstName());
    }
}