package com.lardi.phonebook.controller;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.UserService;
import com.lardi.phonebook.service.ValidationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Controller
public class AuthController {
    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserService userService;

    @RequestMapping("/signin")
    public String signin(){
        return "signin";
    }

    @RequestMapping("/registration/success")
    public String afterRegistration(Model model){
        model.addAttribute("message", "Вы успешно зарегестрировались.");
        return "forward:/signin";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationForm(){
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> registration(@RequestParam Map<String, String> parameters){
        Map<String, String> result = null;
        try {
            User user = parseUserFromRequest(parameters);
            result = validationService.verifyNewUser(user, parameters.get("passwordconfirmation"));
            if(result.isEmpty()){
                userService.save(user);
            }
        } catch (PersistenceException e) {
            result.put("messagefield", "Ошибка при работе с базой данных, попробуйте позже.");
            LOGGER.error(e);
        }
        if(result.isEmpty()){
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

    }

    private User parseUserFromRequest(Map<String, String> parameters){
        User user = new User();
        user.setName(parameters.get("name"));
        user.setLogin(parameters.get("login"));
        user.setPassword(parameters.get("password"));
        return user;
    }
}
