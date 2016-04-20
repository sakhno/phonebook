package com.lardi.phonebook.controller;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.ContactService;
import com.lardi.phonebook.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Controller
public class MainPageController {
    private static final Logger LOGGER = LogManager.getLogger(MainPageController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;


    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String mainPage(Model model, Principal principal) {
        try {
            User currentUser = userService.getUserByLogin(principal.getName());
            List<Contact> userContacts = contactService.findAllUsersContacts(currentUser);
            model.addAttribute("contacts", userContacts);
            model.addAttribute("username", currentUser.getName());
        } catch (PersistenceException e) {
            LOGGER.error(e);
        }
        return "home";
    }
}
