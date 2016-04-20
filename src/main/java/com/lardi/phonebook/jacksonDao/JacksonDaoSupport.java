package com.lardi.phonebook.jacksonDao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lardi.phonebook.dao.PersistenceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
public class JacksonDaoSupport {
    private static final Logger LOGGER = LogManager.getLogger(JacksonDaoSupport.class);

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private File sourceFile;

    public JsonPhonebookModel readData() throws PersistenceException {
        try {
            return mapper.readValue(sourceFile, JsonPhonebookModel.class);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new PersistenceException(e);
        }
    }

    public void writeData(JsonPhonebookModel data) throws PersistenceException {
        sourceFile.delete();
        try {
            sourceFile.createNewFile();
            mapper.writeValue(sourceFile, data);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new PersistenceException(e);
        }
    }
}
