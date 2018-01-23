package service;

import dao.UserDao;
import dao.UserDaoImpl;

public class UserSerciveImpl implements UserService {
    @Override
    public String getPasswordByUserid(String userid) {
        return UserDaoImpl.getInstance().findPasswordByUserid(userid);
    }
}
