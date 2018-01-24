package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSerciveImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String Login(String email, String password) {
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

    @Override
    public String Register(String username, String email, String password) {
        String cor_password = userDao.findPasswordByEmail(email);
        String result = "";
        if(cor_password!=null){
            result = "has register";
        }
        int num = Integer.parseInt(userDao.getMaxUserid())+1;
        String userid = String.valueOf(num);
        User user = new User(userid,username,password,email,1,0,null,0,0);
        userDao.save(user);
        result = userid;
        return result;
    }
}
