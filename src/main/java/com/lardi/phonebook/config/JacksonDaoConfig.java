package com.lardi.phonebook.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lardi.phonebook.jacksonDao.JsonPhonebookModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Configuration
@Profile("jsonstore")
public class JacksonDaoConfig {
    private static final Logger LOGGER = LogManager.getLogger(JacksonDaoConfig.class);

    @Bean
    public File jsonFile() throws IOException{
        File file = new File("src/main/resources/phonebook.json");
        if (file.createNewFile()){
            JsonPhonebookModel data = new JsonPhonebookModel();
            data.setUsers(new HashMap<>());
            objectMapper().writeValue(file, data);
        }
        return file;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
