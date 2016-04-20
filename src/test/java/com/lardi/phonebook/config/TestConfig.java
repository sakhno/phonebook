package com.lardi.phonebook.config;

import com.lardi.phonebook.service.ContactService;
import com.lardi.phonebook.service.UserService;
import com.lardi.phonebook.service.ValidationService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Configuration
@Profile("test")
@ComponentScan("com.lardi.phonebook.controller")
public class TestConfig {

    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public ContactService contactService() {
        return Mockito.mock(ContactService.class);
    }

    @Bean
    public ValidationService validationService() {
        return Mockito.mock(ValidationService.class);
    }
}
