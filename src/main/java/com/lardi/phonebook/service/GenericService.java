package com.lardi.phonebook.service;

import com.lardi.phonebook.dao.PersistenceException;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public interface GenericService<T> {
    T save(T object) throws PersistenceException;

    void delete(long id) throws PersistenceException;
}
