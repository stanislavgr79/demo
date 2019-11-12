package com.example.demo.controller;

import com.example.demo.security.RegistrationForm;
import com.example.demo.security.RegistrationValidator;
import com.example.demo.service.CustomerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RegistrationControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private RegistrationValidator registrationValidator;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(registrationValidator);
    }

    @Before
    public void setUp () {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @After
    public void teardown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void shouldGetNewRegistrationFormToRegisterPage() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/user/registration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("registerForm"))
                .andExpect(MockMvcResultMatchers.view().name("register"));

        MvcResult mvcResult  = result.andReturn();
        RegistrationForm registrationForm = (RegistrationForm) mvcResult.getRequest().getAttribute("registerForm");
        assertThat(registrationForm.getId()).isEqualTo(null);
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/user/registration");
    }

    @Test
    public void shouldRedirectToRegisterAgain_CatchErrorRegistrationForm() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/user/registration")
                .param("firstName","none"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("registerForm"))
                .andExpect(MockMvcResultMatchers.view().name("register"));

        MvcResult mvcResult  = result.andReturn();
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/user/registration");
    }

    @Test
    public void shouldRedirectToLoginPage_SuccessRegistrationForm() throws Exception {
        doNothing().when(customerService).createCustomer(any(RegistrationForm.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/user/registration")
                .param("firstName","Customer")
                .param("lastName","Customer")
                .param("country","country")
                .param("city","city")
                .param("street","street")
                .param("flat","flat")
                .param("email","email@email.ru")
                .param("password","pass")
                .param("confirmPassword","pass"))
                .andExpect(model().attributeExists("registrationSuccess"))
                .andExpect(MockMvcResultMatchers.view().name("login"));

        MvcResult mvcResult  = result.andReturn();
        verify(customerService, times(1)).createCustomer(any(RegistrationForm.class));
        assertThat(mvcResult.getRequest().getPathInfo()).isEqualTo("/user/registration");
    }
}