package com.lardi.phonebook.jdbcDao.mapper;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.dao.UserDao;
import com.lardi.phonebook.model.Contact;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Repository
public class ContactRowMapper implements RowMapper<Contact> {
    private static final Logger LOGGER = LogManager.getLogger(ContactRowMapper.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Contact mapRow(ResultSet resultSet, int i) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong("id"));
        contact.setFirstName(resultSet.getString("firstname"));
        contact.setLastName(resultSet.getString("lastname"));
        contact.setMiddleName(resultSet.getString("middlename"));
        contact.setMobilePhone(resultSet.getString("mobilephone"));
        contact.setHomePhone(resultSet.getString("homephone"));
        contact.setAddress(resultSet.getString("address"));
        contact.setEmail(resultSet.getString("email"));
        try {
            contact.setUser(userDao.read(resultSet.getLong("user_id")));
        } catch (PersistenceException e) {
            LOGGER.error(e);
        }
        return contact;
    }
}
