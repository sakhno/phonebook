package com.lardi.phonebook.dao;

import com.lardi.phonebook.PhonebookApplication;
import com.lardi.phonebook.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PhonebookApplication.class)
@WebAppConfiguration
@ActiveProfiles("jsonstore")
public class UserJDBCDaoImplTest {
    private static final Logger LOGGER = LogManager.getLogger(UserJDBCDaoImplTest.class);
    private static final String USER_LOGIN = "testlogin";
    private static final String USER_LOGIN_CHANGED = "testloginafterchanges";
    private static final String USER_NAME = "testname";
    private static final String USER_PASSWORD = "testpassword";

    @Autowired
    private UserDao userDao;

    @Test
    public void createUpdateDeleteTest(){
        User user = new User();
        user.setLogin(USER_LOGIN);
        user.setName(USER_NAME);
        user.setPassword(USER_PASSWORD);

        try {
            //adding user to database
            User userFromDB = userDao.create(user);
            assertEquals(user, userFromDB);

            //change user loggin and updating database entry
            userFromDB.setLogin(USER_LOGIN_CHANGED);
            userDao.update(userFromDB);

            //read updated user from DB
            user = userFromDB;
            userFromDB = userDao.read(userFromDB.getId());
            assertEquals(user, userFromDB);

            //read all users
            List<User> allUsers = userDao.readAll();
            assertTrue(allUsers!=null&&allUsers.size()>0);

            //read user by login
            assertNotNull(userDao.readBylogin(USER_LOGIN_CHANGED));

            //deleting user from DB
            userDao.delete(userFromDB.getId());
            userFromDB = userDao.read(userFromDB.getId());
            assertNull(userFromDB);
        } catch (PersistenceException e) {
            LOGGER.error(e);
        }
    }
}
