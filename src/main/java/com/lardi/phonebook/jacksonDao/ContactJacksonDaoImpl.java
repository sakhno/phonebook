package com.lardi.phonebook.jacksonDao;

import com.lardi.phonebook.dao.ContactDao;
import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Repository
@Profile("default")
public class ContactJacksonDaoImpl extends JacksonDaoSupport implements ContactDao {
    @Override
    public List<Contact> getAllContactsByUser(User user) throws PersistenceException {
        JsonPhonebookModel data = readData();
        return data.getUsers().get(user.getLogin()).getContacts();
    }

    @Override
    public Contact create(Contact contact) throws PersistenceException {
        JsonPhonebookModel data = readData();
        //getting current user
        User user = data.getUsers().get(contact.getUser().getLogin());
        //check if user already has contacts, if not setting empty List
        if (user.getContacts() == null) {
            user.setContacts(new ArrayList<>());
        }
        List<Contact> userContacts = user.getContacts();
        long contactId = data.getContactCount() + 1;
        contact.setId(contactId);
        data.setContactCount(contactId);
        userContacts.add(contact);
        writeData(data);
        return contact;
    }

    @Override
    public Contact read(long id) throws PersistenceException {
        JsonPhonebookModel data = readData();
        for (User user : data.getUsers().values()) {
            List<Contact> userContacts = user.getContacts();
            for (Contact contact : userContacts) {
                if (contact.getId() == id) {
                    contact.setUser(user);
                    return contact;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Contact object) throws PersistenceException {
        JsonPhonebookModel data = readData();
        List<Contact> userContacts = data.getUsers().get(object.getUser().getLogin()).getContacts();
        for (int i = 0; i < userContacts.size(); i++) {
            if (userContacts.get(i).getId() == object.getId()) {
                userContacts.set(i, object);
                writeData(data);
                break;
            }
        }
    }

    @Override
    public void delete(long id) throws PersistenceException {
        JsonPhonebookModel data = readData();
        for (Map.Entry<String, User> entry : data.getUsers().entrySet()) {
            List<Contact> contacts = entry.getValue().getContacts();
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getId() == id) {
                    contacts.remove(i);
                    writeData(data);
                    return;
                }
            }
        }
    }

    @Override
    public List<Contact> readAll() throws PersistenceException {
        JsonPhonebookModel data = readData();
        List<Contact> result = new ArrayList<>();
        for (User user : data.getUsers().values()) {
            result.addAll(user.getContacts());
        }
        return result;
    }
}
