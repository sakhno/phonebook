package com.lardi.phonebook.service;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;

import java.util.Map;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public interface ValidationService {
    Map<String, String> verifyUser(User user, String passwordConfirmation) throws PersistenceException;

    Map<String, String> verifyContact(Contact contact);
}
