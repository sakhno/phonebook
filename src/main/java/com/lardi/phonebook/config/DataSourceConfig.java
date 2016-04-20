package com.lardi.phonebook.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Configuration
public class DataSourceConfig {
    private static final Logger LOGGER = LogManager.getLogger(DataSourceConfig.class);

    @Bean
    @Profile("mysql")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(System.getProperty("url"));
        dataSource.setUsername(System.getProperty("username"));
        dataSource.setPassword(System.getProperty("password"));
        dataSource.setInitialSize(10);
        dataSource.setMaxTotal(70);
        dataSource.setMaxIdle(30);
        return dataSource;
    }

    @Bean
    @Profile("heroku")
    public DataSource herokuDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            LOGGER.error(e);
        }
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath());
        dataSource.setUsername(dbUri.getUserInfo().split(":")[0]);
        dataSource.setPassword(dbUri.getUserInfo().split(":")[1]);
        dataSource.setInitialSize(10);
        dataSource.setMaxTotal(70);
        dataSource.setMaxIdle(30);
        return dataSource;
    }

    @Bean
    @Profile("default")
    public DataSource emptyDataSource() {
        return new BasicDataSource();
    }
}
