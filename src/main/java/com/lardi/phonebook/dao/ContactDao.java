package com.lardi.phonebook.dao;

import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;

import java.util.List;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public interface ContactDao extends GenericDao<Contact> {
    List<Contact> getAllContactsByUser(User user) throws PersistenceException;
}
