package com.lardi.phonebook.dao;

import com.lardi.phonebook.model.User;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public interface UserDao extends GenericDao<User> {
    User readBylogin(String login) throws PersistenceException;
}
