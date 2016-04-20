package com.lardi.phonebook.controller;

import com.lardi.phonebook.config.TestConfig;
import com.lardi.phonebook.config.WebConfig;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.UserService;
import com.lardi.phonebook.service.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class AuthControllerTest {
    //user fields
    private static final String USER_LOGIN = "testlogin";
    private static final String USER_NAME = "testname";
    private static final String USER_PASSWORD = "testpassword";

    private User user;

    private MockMvc mockMvc;

    @Autowired
    private UserService userServiceMock;
    @Autowired
    private ValidationService validationServiceMock;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(userServiceMock, validationServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User();
        user.setName(USER_NAME);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);
    }

    @Test
    public void registrationTest() throws Exception {
        when(validationServiceMock.verifyUser(user, USER_PASSWORD)).thenReturn(new HashMap<>());
        when(userServiceMock.save(user)).thenReturn(user);

        mockMvc.perform(post("/registration")
                .param("name", USER_NAME)
                .param("login", USER_LOGIN)
                .param("password", USER_PASSWORD)
                .param("passwordconfirmation", USER_PASSWORD))
                .andExpect(status().isOk());

        verify(validationServiceMock, times(1)).verifyUser(user, USER_PASSWORD);
        verify(userServiceMock, times(1)).save(user);
    }

}
