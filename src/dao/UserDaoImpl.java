package dao;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {

    }

    @Override
    public void delete(String userid) {

    }

    @Override
    public void findUserByUserid(String userid) {

    }

    @Override
    public void findPasswordByUserid(String userid) {

    }
}
