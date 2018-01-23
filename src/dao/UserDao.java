package dao;

import model.User;

public interface UserDao {
    public void save(User user);

    public void delete(String userid);

    public void findUserByUserid(String userid);

    public void findPasswordByUserid(String userid);
}
