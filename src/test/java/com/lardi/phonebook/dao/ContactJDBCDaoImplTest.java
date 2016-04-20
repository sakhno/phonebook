package com.lardi.phonebook.dao;

import com.lardi.phonebook.PhonebookApplication;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PhonebookApplication.class)
@WebAppConfiguration
@ActiveProfiles("jsonstore")
public class ContactJDBCDaoImplTest {
    private static final Logger LOGGER = LogManager.getLogger(ContactJDBCDaoImplTest.class);
    //contact fields
    private static final String CONTACT_LAST_NAME = "testlastname";
    private static final String CONTACT_FIRST_NAME = "testfirstname";
    private static final String CONTACT_MIDDLE_NAME = "testmiddlename";
    private static final String CONTACT_MOBILE_PHONE = "testmobilephone";
    private static final String CONTACT_HOME_PHONE = "testhomephone";
    private static final String CONTACT_ADDRESS = "testaddress";
    private static final String CONTACT_EMAIL = "testemail";
    private static final String CONTACT_CHANGED_FIRST_NAME = "changedname";
    //user fields
    private static final String USER_LOGIN = "testlogin";
    private static final String USER_NAME = "testname";
    private static final String USER_PASSWORD = "testpassword";

    private User user;
    private Contact contact;

    @Autowired
    private ContactDao contactDao;
    @Autowired
    private UserDao userDao;

    @Test
    public void createUpdateDeleteTest(){
        try{
            Contact tempContact = contact;
            //adding user to DB
            contact = contactDao.create(contact);
            assertEquals(tempContact, contact);

            //changing contact first name and updating DB entry
            contact.setFirstName(CONTACT_CHANGED_FIRST_NAME);
            contactDao.update(contact);

            tempContact = contact;
            //reading updated contact
            contact = contactDao.read(contact.getId());
            assertEquals(tempContact, contact);

            //read all users
            List<Contact> allContacts = contactDao.readAll();
            assertTrue(allContacts!=null&&allContacts.size()>0);

            //deleting user from DB
            contactDao.delete(contact.getId());
            contact = contactDao.read(contact.getId());
            assertNull(contact);

        } catch (PersistenceException e) {
            LOGGER.error(e);
        }
    }

    @Test
    public void getAllContactsByUserTest(){
        try{
            List<Contact> contactsToDelete = new ArrayList<>();
            //creating test contacts and puting them to DB
            for(int i = 0;i<4; i++){
                Contact contact = new Contact();
                contact.setFirstName(CONTACT_FIRST_NAME+i);
                contact.setLastName(CONTACT_LAST_NAME+i);
                contact.setMiddleName(CONTACT_MIDDLE_NAME+i);
                contact.setMobilePhone(CONTACT_MOBILE_PHONE+i);
                contact.setHomePhone(CONTACT_HOME_PHONE+i);
                contact.setAddress(CONTACT_ADDRESS+i);
                contact.setEmail(CONTACT_EMAIL+i);
                contact.setUser(user);
                contactsToDelete.add(contactDao.create(contact));
            }

            //retrieving all contacts by user
            List<Contact> contacts = contactDao.getAllContactsByUser(user);
            assertEquals(4, contacts.size());

            //deleting test contacts
            for(Contact contact: contactsToDelete){
                contactDao.delete(contact.getId());
            }
        }catch (PersistenceException e){
            LOGGER.error(e);
        }
    }

    @Before
    public void initTestData(){
        user = new User();
        user.setLogin(USER_LOGIN);
        user.setName(USER_NAME);
        user.setPassword(USER_PASSWORD);
        try {
            user = userDao.create(user);
        } catch (PersistenceException e) {
            LOGGER.error(e);
        }

        contact = new Contact();
        contact.setFirstName(CONTACT_FIRST_NAME);
        contact.setLastName(CONTACT_LAST_NAME);
        contact.setMiddleName(CONTACT_MIDDLE_NAME);
        contact.setMobilePhone(CONTACT_MOBILE_PHONE);
        contact.setHomePhone(CONTACT_HOME_PHONE);
        contact.setAddress(CONTACT_ADDRESS);
        contact.setEmail(CONTACT_EMAIL);
        contact.setUser(user);
    }

    @After
    public void deleteUser(){
        try {
            userDao.delete(user.getId());
        } catch (PersistenceException e) {
            LOGGER.error(e);
        }
    }

}
