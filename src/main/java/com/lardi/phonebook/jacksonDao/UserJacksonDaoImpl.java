package com.lardi.phonebook.jacksonDao;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.dao.UserDao;
import com.lardi.phonebook.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Repository
@Profile("jsonstore")
public class UserJacksonDaoImpl extends JacksonDaoSupport implements UserDao {

    @Override
    public User readBylogin(String login) throws PersistenceException {
        JsonPhonebookModel data = readData();
        if(data.getUsers()!=null){
            return data.getUsers().get(login);
        }else {
            return null;
        }
    }

    @Override
    public User create(User object) throws PersistenceException {
        JsonPhonebookModel data = readData();
        long userId = data.getUserCount()+1;
        object.setId(userId);
        object.setContacts(new ArrayList<>());
        data.setUserCount(userId);
        if(data.getUsers()==null){
            data.setUsers(new HashMap<>());
        }
        data.getUsers().put(object.getLogin(), object);
        writeData(data);
        return object;
    }

    @Override
    public User read(long id) throws PersistenceException {
        JsonPhonebookModel data = readData();
        for(Map.Entry<String, User> entry: data.getUsers().entrySet()){
            if(entry.getValue().getId()==id){
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void update(User object) throws PersistenceException {
        JsonPhonebookModel data = readData();
        for(User user: data.getUsers().values()){
            if(user.getId()==object.getId()){
                data.getUsers().remove(user.getLogin());
                break;
            }
        }
        data.getUsers().put(object.getLogin(), object);
        writeData(data);
    }

    @Override
    public void delete(long id) throws PersistenceException {
        JsonPhonebookModel data = readData();
        User user = read(id);
        data.getUsers().remove(user.getLogin());
        writeData(data);
    }

    @Override
    public List<User> readAll() throws PersistenceException {
        return new ArrayList<>(readData().getUsers().values());
    }
}
