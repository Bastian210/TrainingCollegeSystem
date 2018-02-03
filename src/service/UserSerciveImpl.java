package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.Payment;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MailUtil;
import utils.Param;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSerciveImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String Login(String email, String password) {
        User user = userDao.findUserByEmail(email);
        String result = "";
        if(user==null){
            result = "wrong email";
        }else if(user.getPassword().equals(password)){
            Param.setUserid(user.getUserid());
            result = "success";
        }else{
            result = "wrong password";
        }
        return result;
    }

    @Override
    public String ManagerLogin(String id, String password) {
        User user = userDao.findUserByUserid(id);
        if(user.getIsadmin()!=1){
            return "wrong id";
        }
        if(!user.getPassword().equals(password)){
            return "wrong password";
        }
        return "success";
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
        User cor_user = userDao.findUserByEmail(email);
        String result = "";
        if(!cor_code.equals(code)){
            return "wrong code";
        }
        if(cor_user!=null){
            return "has register";
        }
        int num = Integer.parseInt(userDao.getMaxUserid())+1;
        String userid = String.valueOf(num);
        User user = new User(userid,username,password,email,null,null,1,0,null,0,0);
        userDao.save(user);
        result = userid;
        return result;
    }

    @Override
    public Map getUserMessage(String userid) {
        User user = userDao.findUserByUserid(userid);
        Map<String,String> map = new HashMap<>();
        map.put("username",user.getUsername());
        map.put("gender",user.getGender());
        map.put("education",user.getEducation());
        if(user.getPayid()==null){
            map.put("payid","");
            map.put("balance","");
        }else{
            map.put("payid",user.getPayid());
            Payment payment = userDao.findPaymentByPayId(user.getPayid());
            map.put("balance", String.valueOf(payment.getBalance()));
        }
        return map;
    }

    @Override
    public void ChangeUserMessage(String userid, String username, String gender, String education) {
        userDao.updateMessageByUserId(userid, username, gender, education);
    }

    @Override
    public String BindAccount(String userid, String payid, String password) {
        Payment payment = userDao.findPaymentByPayId(payid);
        if(payment==null){
            return "wrong id";
        }else if(!payment.getPassword().equals(password)){
            return "wrong password";
        }else{
            userDao.updatePayIdByUserId(userid,payid);
            return "success";
        }
    }

    @Override
    public void UnbindAccount(String userid, String payid) {
        userDao.updatePayIdByUserId(userid,"");
    }

    @Override
    public String ChangePaymentPassword(String payid, String oldPassword, String newPassword) {
        Payment payment = userDao.findPaymentByPayId(payid);
        if(!payment.getPassword().equals(oldPassword)){
            return "wrong password";
        }
        userDao.updatePasswordByPayid(payid,newPassword);
        return "success";
    }
}
