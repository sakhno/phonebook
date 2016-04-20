package com.lardi.phonebook.jacksonDao;

import com.lardi.phonebook.model.User;

import java.util.Map;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public class JsonPhonebookModel {
    private long userCount;
    private long contactCount;
    private Map<String, User> users;

    public Map<String, User> getUsers() {
        return users;
    }

    public JsonPhonebookModel setUsers(Map<String, User> users) {
        this.users = users;
        return this;
    }

    public long getUserCount() {
        return userCount;
    }

    public JsonPhonebookModel setUserCount(long userCount) {
        this.userCount = userCount;
        return this;
    }

    public long getContactCount() {
        return contactCount;
    }

    public JsonPhonebookModel setContactCount(long contactCount) {
        this.contactCount = contactCount;
        return this;
    }
}
