package com.lardi.phonebook.service.impl;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.dao.UserDao;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    @Override
    public User save(User object) throws PersistenceException {
        if(object.getId()==0){
            object.setPassword(shaPasswordEncoder.encodePassword(object.getPassword(), null));
            return userDao.create(object);
        }else {
            userDao.update(object);
            return object;
        }
    }

    @Override
    public void delete(long id) throws PersistenceException {
        userDao.delete(id);
    }

    @Override
    public User getUserByLogin(String login) throws PersistenceException {
        return userDao.readBylogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("user"));
        User user;
        try {
            user = userDao.readBylogin(login);
            if (user == null) {
                throw new UsernameNotFoundException("user not found");
            }
        } catch (PersistenceException e) {
            LOGGER.error(e);
            throw new UsernameNotFoundException("error reading user from DB");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
    }
}
