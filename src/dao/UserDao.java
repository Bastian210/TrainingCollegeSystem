package dao;

import model.User;

public interface UserDao {
    public void save(User user);

    public void delete(String userid);

    public User findUserByUserid(String userid);

    public String findPasswordByEmail(String email);

    public String getMaxUserid();
}
