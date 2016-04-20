package com.lardi.phonebook.controller;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.service.ContactService;
import com.lardi.phonebook.service.UserService;
import com.lardi.phonebook.service.ValidationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Controller
@RequestMapping("/contact")
public class ContactController {

    private static final Logger LOGGER = LogManager.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Contact getContact(@PathVariable long id) {
        Contact contact = null;
        try {
            contact = contactService.findContactById(id);
        } catch (PersistenceException e) {
            LOGGER.error(e);
        }
        return contact;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteContact(@PathVariable long id) {
        try {
            contactService.delete(id);
        } catch (PersistenceException e) {
            LOGGER.error(e);
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Validating contact
     *
     * @return map with html element id as key and error message as value.
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> verifyContact(@RequestParam Map<String, String> params, Principal principal) {
        Contact contact = parseContactFromRequest(params);
        Map<String, String> result = validationService.verifyContact(contact);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> addContact(@RequestParam Map<String, String> params, Principal principal) {
        try {
            Contact contact = parseContactFromRequest(params);
            contact.setUser(userService.getUserByLogin(principal.getName()));
            Long contactId = contactService.save(contact).getId();
            return new ResponseEntity(contactId, HttpStatus.OK);
        } catch (PersistenceException e) {
            LOGGER.error(e);
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    private Contact parseContactFromRequest(Map<String, String> params) {
        Contact contact = new Contact();
        String contactId = params.get("id");
        if ("".equals(contactId)) {
            contact.setId(0);
        } else {
            contact.setId(Long.parseLong(params.get("id")));
        }
        contact.setLastName(params.get("lastname"));
        contact.setFirstName(params.get("firstname"));
        contact.setMiddleName(params.get("middlename"));
        contact.setMobilePhone(params.get("mobilephone"));
        contact.setHomePhone(params.get("homephone"));
        contact.setAddress(params.get("address"));
        contact.setEmail(params.get("email"));
        return contact;
    }
}
