package com.lardi.phonebook.service.impl;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.dao.UserDao;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    //regexp patterns
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String MOBILE_PATTERN = "^\\+?38\\(?0\\d{2}\\)?\\d{3}-?\\d{2}-?\\d{2}$";
    private static final String LOGIN_PATTERN = "^[A-Za-z0-9]{3,}$";

    //error messages
    private static final String REJECT_EMPTY = "Обязательное поле";
    private static final String LESS_THEN_5 = "Не менее 5 символов";
    private static final String LESS_THEN_4 = "Не менее 4 символов";
    private static final String LOGIN_REQUIREMENTS = "Не менее 3 английских букв или цифр";
    private static final String PASSWORDS_DONT_MATCH = "Пароли не совпадают";
    private static final String LOGIN_EXISTS = "Пользователь с таким логином уже существует в базе";
    private static final String MOBILE_PHONE_FORMAT = "Некоректный номер телефона";
    private static final String EMAIL_FORMAT = "Некоректный email";

    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, String> verifyContact(Contact contact) {
        Map<String, String> result = new HashMap<>();
        if (contact.getLastName().length() < 4) {
            result.put("lastname", LESS_THEN_4);
        }
        if (contact.getFirstName().length() < 4) {
            result.put("firstname", LESS_THEN_4);
        }
        if (contact.getMiddleName().length() < 4) {
            result.put("middlename", LESS_THEN_4);
        }
        if ("".equals(contact.getMobilePhone())) {
            result.put("mobilephone", REJECT_EMPTY);
        } else if (!validate(MOBILE_PATTERN, contact.getMobilePhone())) {
            result.put("mobilephone", MOBILE_PHONE_FORMAT);
        }

        if (!"".equals(contact.getHomePhone()) && !validate(MOBILE_PATTERN, contact.getHomePhone())) {
            result.put("homephone", MOBILE_PHONE_FORMAT);
        }
        if (!"".equals(contact.getEmail()) && !validate(EMAIL_PATTERN, contact.getEmail())) {
            result.put("email", EMAIL_FORMAT);
        }
        return result;
    }

    @Override
    public Map<String, String> verifyNewUser(User user, String passwordConfirmation) throws PersistenceException {
        Map<String, String> result = new HashMap<>();
        if (user.getName().length() < 5) {
            result.put("name", LESS_THEN_5);
        }
        if (!validate(LOGIN_PATTERN, user.getLogin())) {
            result.put("login", LOGIN_REQUIREMENTS);
        } else if (userDao.readBylogin(user.getLogin()) != null) {
            result.put("login", LOGIN_EXISTS);
        }
        if (user.getPassword().length() < 5) {
            result.put("password", LESS_THEN_5);
        }
        if (!user.getPassword().equals(passwordConfirmation)) {
            result.put("passwordconfirmation", PASSWORDS_DONT_MATCH);
        }
        return result;
    }

    private boolean validate(String pattern, String value) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
    }

}
