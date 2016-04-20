package com.lardi.phonebook.dao;

import java.util.List;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public interface GenericDao<T> {
    T create(T object) throws PersistenceException;

    T read(long id)throws PersistenceException;

    void update(T object)throws PersistenceException;

    void delete(long id)throws PersistenceException;

    List<T> readAll() throws PersistenceException;
}
