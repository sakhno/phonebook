package com.lardi.phonebook.service;

import com.lardi.phonebook.config.TestConfig;
import com.lardi.phonebook.config.WebConfig;
import com.lardi.phonebook.dao.UserDao;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.impl.ValidationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = {WebConfig.class, TestConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class ValidationServiceImplTest {
    //contact fields
    private static final String CONTACT_LAST_NAME = "testlastname";
    private static final String CONTACT_FIRST_NAME = "testfirstname";
    private static final String CONTACT_MIDDLE_NAME = "testmiddlename";
    private static final String CONTACT_MOBILE_PHONE = "+380983332222";
    private static final String CONTACT_HOME_PHONE = "380445555555";
    private static final String CONTACT_ADDRESS = "testaddress";
    private static final String CONTACT_EMAIL = "testemail@domain.com";
    //bad contact
    private static final String BAD_CONTACT_LAST_NAME = "ame";
    private static final String BAD_CONTACT_FIRST_NAME = "ame";
    private static final String BAD_CONTACT_MIDDLE_NAME = "ame";
    private static final String BAD_CONTACT_MOBILE_PHONE = "+3809832222";
    private static final String BAD_CONTACT_EMAIL = "testemail@domain";


    //user fields
    private static final String USER_LOGIN = "testlogin";
    private static final String USER_NAME = "testname";
    private static final String USER_PASSWORD = "testpassword";
    //bad user fields
    private static final String BAD_USER_LOGIN_SHORT = "lo";
    private static final String BAD_USER_LOGIN_RUSSIAN = "логин";
    private static final String BAD_USER_NAME_SHORT = "name";
    private static final String BAD_USER_PASSWORD_SHORT = "word";
    @InjectMocks
    private final ValidationService validationService = new ValidationServiceImpl();
    private Contact contact;
    private User user;
    private User badUser;
    private Contact badContact;
    @Mock
    private UserDao userDao;

    @Before
    public void setUp() {
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

        badUser = new User();
        badUser.setLogin(BAD_USER_LOGIN_SHORT);
        badUser.setName(BAD_USER_NAME_SHORT);
        badUser.setPassword(BAD_USER_PASSWORD_SHORT);

        badContact = new Contact();
        badContact.setLastName(BAD_CONTACT_LAST_NAME);
        badContact.setFirstName(BAD_CONTACT_FIRST_NAME);
        badContact.setMiddleName(BAD_CONTACT_MIDDLE_NAME);
        badContact.setMobilePhone(BAD_CONTACT_MOBILE_PHONE);
        badContact.setEmail(BAD_CONTACT_EMAIL);
    }

    @Test
    public void successfullVerifyUser() throws Exception {
        when(userDao.readBylogin(USER_LOGIN)).thenReturn(null);
        Map<String, String> result = validationService.verifyUser(user, USER_PASSWORD);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void userAllreadyExists() throws Exception {
        when(userDao.readBylogin(USER_LOGIN)).thenReturn(user);
        Map<String, String> result = validationService.verifyUser(user, USER_PASSWORD);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void badUser() throws Exception {
        when(userDao.readBylogin(USER_LOGIN)).thenReturn(null);
        Map<String, String> result = validationService.verifyUser(badUser, USER_PASSWORD);
        assertNotNull(result);
        assertEquals(4, result.size());//short name, short login, short password, password confirmation doesn't match
        badUser.setLogin(BAD_USER_LOGIN_RUSSIAN);
        result = validationService.verifyUser(badUser, USER_PASSWORD);
        assertNotNull(result);
        assertEquals(4, result.size());//short name, russian login, short password, password confirmation doesn't match
    }

    @Test
    public void successfullVerifyContact() throws Exception {
        Map<String, String> result = validationService.verifyContact(contact);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void badContact() {
        Map<String, String> result = validationService.verifyContact(badContact);
        assertNotNull(result);
        assertEquals(5, result.size());
    }

}
