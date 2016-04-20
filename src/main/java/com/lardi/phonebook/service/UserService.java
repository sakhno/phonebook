package com.lardi.phonebook.service;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public interface UserService extends GenericService<User>, UserDetailsService {
    User getUserByLogin(String login) throws PersistenceException;
}
