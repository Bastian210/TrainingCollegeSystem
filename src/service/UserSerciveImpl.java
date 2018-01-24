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
    public String login(String email, String password) {
        String cor_password = userDao.findPasswordByEmail(email);

        String result = "";
        if(cor_password==null){
            result = "wrong email";
        }else if(cor_password.equals(password)){
            result = "success";
        }else{
            result = "wrong password";
        }
        return result;
    }
}
