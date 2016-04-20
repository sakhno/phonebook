package com.lardi.phonebook.jdbcDao;

import com.lardi.phonebook.dao.PersistenceException;
import com.lardi.phonebook.dao.UserDao;
import com.lardi.phonebook.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Repository
@Profile("default")
public class UserJDBCDaoImpl extends JdbcDaoSupport implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserJDBCDaoImpl.class);
    private static final String CREATE_QUERY = "INSERT INTO user (login, password, name) VALUES (?, ?, ?)";
    private static final String READ_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE user SET login = ?, password = ?, name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user WHERE id = ";
    private static final String READ_ALL_QUERY = "SELECT * FROM user";
    private static final String READ_BY_LOGIN = "SELECT * FROM user WHERE login = ?";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RowMapper<User> userRowMapper;

    @PostConstruct
    private void initializer(){
        setDataSource(dataSource);
    }

    @Override
    public User create(User object) throws PersistenceException {
        KeyHolder holder = new GeneratedKeyHolder();
        getJdbcTemplate().update(connection -> {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, object.getLogin());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getName());
            return statement;
        }, holder);
        return read((Long) holder.getKeys().get("GENERATED_KEY"));
    }

    @Override
    public User read(long id) throws PersistenceException {
        try {
            return getJdbcTemplate().queryForObject(READ_QUERY, new Object[]{id}, userRowMapper);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void update(User object) throws PersistenceException {
        getJdbcTemplate().update(connection -> {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, object.getLogin());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getName());
            statement.setLong(4, object.getId());
            return statement;
        });
    }

    @Override
    public void delete(long id) throws PersistenceException {
        getJdbcTemplate().update(DELETE_QUERY+id);
    }

    @Override
    public List<User> readAll() throws PersistenceException {
        return getJdbcTemplate().query(READ_ALL_QUERY, userRowMapper);
    }

    @Override
    public User readBylogin(String login) throws PersistenceException {
        try{
            return getJdbcTemplate().queryForObject(READ_BY_LOGIN, new Object[]{login}, userRowMapper);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
