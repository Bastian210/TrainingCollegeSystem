package service;

import dao.UserDao;
import dao.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSerciveImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String getPasswordByUserid(String userid) {
        return userDao.findPasswordByUserid(userid);
    }
}
