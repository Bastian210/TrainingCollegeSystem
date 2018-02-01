package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MailUtil;

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
    public void SendCode(String email){
        int num = (int) (Math.random()*900000+100000);
        String code = String.valueOf(num);
        String content = "您用于注册Training College的验证码为"+code+"。";
        userDao.saveCode(email,code);
        MailUtil.sendMail(email,"邮箱验证",content);
    }

    @Override
    public String Register(String username, String email, String password, String code) {
        String cor_code = userDao.getCode(email);
        String cor_password = userDao.findPasswordByEmail(email);
        String result = "";
        if(!cor_code.equals(code)){
            return "wrong code";
        }
        if(cor_password!=null){
            return "has register";
        }
        int num = Integer.parseInt(userDao.getMaxUserid())+1;
        String userid = String.valueOf(num);
        User user = new User(userid,username,password,email,1,0,null,0,0);
        userDao.save(user);
        result = userid;
        return result;
    }
}
