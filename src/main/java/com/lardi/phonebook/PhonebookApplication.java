package com.lardi.phonebook;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class PhonebookApplication {
    private static final Logger LOGGER = LogManager.getLogger(PhonebookApplication.class);

    public static void main(String[] args) {
        systemVariablesSetUp();
        SpringApplication.run(PhonebookApplication.class, args);
    }

    private static void systemVariablesSetUp() {
        String path = System.getProperty("lardi.conf");
        //if no config file - set variable for heroku postgres and axit method
        if (path == null) {
            System.setProperty("GENERATED_KEY_NAME", "id");
            return;
        }

        //reading properties from config file
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = new FileInputStream(path);
            properties.load(stream);
        } catch (FileNotFoundException e) {
            LOGGER.info(e);
        } catch (IOException e) {
            LOGGER.info(e);
        }

        //setting config properties as system variables
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            System.setProperty((String) entry.getKey(), (String) entry.getValue());
        }

        //setting spring profile from properties
        String profile = properties.getProperty("profile");
        if (profile != null) {
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, profile);
            if (profile.equals("mysql")) {
                System.setProperty("GENERATED_KEY_NAME", "GENERATED_KEY");
            }
        }
    }
}
