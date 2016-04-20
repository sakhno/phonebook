package com.lardi.phonebook.controller;

import com.lardi.phonebook.config.TestConfig;
import com.lardi.phonebook.config.WebConfig;
import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.ContactService;
import com.lardi.phonebook.service.UserService;
import com.lardi.phonebook.service.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.security.Principal;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class ContactControllerTest {
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

    private Contact contact;
    private User user;
    private Principal principal;

    private MockMvc mockMvc;
    @Autowired
    private UserService userServiceMock;
    @Autowired
    private ContactService contactServiceMock;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        Mockito.reset(userServiceMock, contactServiceMock, validationService);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        contact = new Contact();
        contact.setId(1L);
        contact.setLastName(CONTACT_LAST_NAME);
        contact.setFirstName(CONTACT_FIRST_NAME);
        contact.setMiddleName(CONTACT_MIDDLE_NAME);
        contact.setMobilePhone(CONTACT_MOBILE_PHONE);
        contact.setHomePhone(CONTACT_HOME_PHONE);
        contact.setAddress(CONTACT_ADDRESS);
        contact.setEmail(CONTACT_EMAIL);
        user = new User();
        user.setName(USER_NAME);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);
        principal = () -> USER_LOGIN;
    }

    @Test
    public void getContactTest() throws Exception {
        when(contactServiceMock.findContactById(1L)).thenReturn(contact);
        mockMvc.perform(get("/contact/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                        MediaType.APPLICATION_JSON.getSubtype(),
                        Charset.forName("utf8")
                )))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.lastName", is(CONTACT_LAST_NAME)))
                .andExpect(jsonPath("$.firstName", is(CONTACT_FIRST_NAME)))
                .andExpect(jsonPath("$.middleName", is(CONTACT_MIDDLE_NAME)))
                .andExpect(jsonPath("$.mobilePhone", is(CONTACT_MOBILE_PHONE)))
                .andExpect(jsonPath("$.homePhone", is(CONTACT_HOME_PHONE)))
                .andExpect(jsonPath("$.address", is(CONTACT_ADDRESS)))
                .andExpect(jsonPath("$.email", is(CONTACT_EMAIL)));

        verify(contactServiceMock, times(1)).findContactById(1L);
        verifyNoMoreInteractions(contactServiceMock);
    }

    @Test
    public void deleteContactTest() throws Exception {
        mockMvc.perform(delete("/contact/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteContactPersistanceExceptionTest() throws Exception {
        Mockito.doThrow(new PersistenceException("")).when(contactServiceMock).delete(1L);
        mockMvc.perform(delete("/contact/{id}", 1L))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void addContactTest() throws Exception {
        when(userServiceMock.getUserByLogin(USER_LOGIN)).thenReturn(user);
        when(contactServiceMock.save(contact)).thenReturn(contact);
        mockMvc.perform(post("/contact")
                .principal(principal)
                .param("id", "")
                .param("lastname", CONTACT_LAST_NAME)
                .param("firstname", CONTACT_FIRST_NAME)
                .param("middlename", CONTACT_MIDDLE_NAME)
                .param("mobilephone", CONTACT_MOBILE_PHONE)
                .param("homephone", CONTACT_HOME_PHONE)
                .param("address", CONTACT_ADDRESS)
                .param("email", CONTACT_EMAIL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1)));
    }
}
