package com.lardi.phonebook.controller;

import com.lardi.phonebook.config.TestConfig;
import com.lardi.phonebook.config.WebConfig;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.ContactService;
import com.lardi.phonebook.service.UserService;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class MainPageControllerTest {
    //contact fields
    private static final String CONTACT_LAST_NAME = "testlastname";
    private static final String CONTACT_FIRST_NAME = "testfirstname";
    private static final String CONTACT_MIDDLE_NAME = "testmiddlename";
    private static final String CONTACT_MOBILE_PHONE = "+380983332222";
    private static final String CONTACT_HOME_PHONE = "testhomephone";
    private static final String CONTACT_ADDRESS = "testaddress";
    private static final String CONTACT_EMAIL = "testemail@domain.com";
    //user fields
    private static final String USER_LOGIN = "testlogin";
    private static final String USER_NAME = "testname";
    private static final String USER_PASSWORD = "testpassword";

    private List<Contact> contacts;
    private User user;
    private Principal principal;

    private MockMvc mockMvc;
    @Autowired
    private UserService userServiceMock;
    @Autowired
    private ContactService contactServiceMock;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(userServiceMock, contactServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        contacts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Contact contact = new Contact();
            contact.setLastName(CONTACT_LAST_NAME + i);
            contact.setFirstName(CONTACT_FIRST_NAME + i);
            contact.setMiddleName(CONTACT_MIDDLE_NAME + i);
            contact.setMobilePhone(CONTACT_MOBILE_PHONE + i);
            contact.setHomePhone(CONTACT_HOME_PHONE + i);
            contact.setAddress(CONTACT_ADDRESS + i);
            contact.setEmail(i + CONTACT_EMAIL);
            contacts.add(contact);
        }
        user = new User();
        user.setName(USER_NAME);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);

        principal = () -> USER_LOGIN;
    }

    @Test
    public void mainPageTest() throws Exception {
        when(userServiceMock.getUserByLogin(USER_LOGIN)).thenReturn(user);
        when(contactServiceMock.findAllUsersContacts(user)).thenReturn(contacts);

        mockMvc.perform(get("/home").principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/home.jsp"))
                .andExpect(model().attribute("username", equalTo(USER_NAME)))
                .andExpect(model().attribute("contacts", hasSize(4)))
                .andExpect(model().attribute("contacts", hasItems(contacts.toArray())));

        verify(userServiceMock, times(1)).getUserByLogin(USER_LOGIN);
        verify(contactServiceMock, times(1)).findAllUsersContacts(user);
    }

}
