package com.lardi.phonebook.service;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;

import java.util.List;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public interface ContactService extends GenericService<Contact> {
    List<Contact> findAllUsersContacts(User user) throws PersistenceException;

    Contact findContactById(long id) throws PersistenceException;
}
