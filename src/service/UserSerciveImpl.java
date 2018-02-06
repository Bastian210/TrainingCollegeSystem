package service;

import dao.PaymentDao;
import dao.UserDao;
import dao.UserDaoImpl;
import model.Payment;
import model.User;
import org.json.JSONObject;
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

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public String Login(String email, String password) {
        User user = userDao.findUserByEmail(email);
        String result = "";
        if(user==null){
            result = "wrong email";
        }else if(!user.getPassword().equals(password)){
            result = "wrong password";
        }else if(user.getWriteoff()==1) {
            result = "write off";
        }else{
            Param.setUserid(user.getUserid());
            result = "success";
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
        User user = new User(userid,username,password,email,null,null,1,0,null,0,0,0);
        userDao.save(user);
        result = userid;
        return result;
    }

    @Override
    public Map GetUserMessage(String userid) {
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
            Payment payment = paymentDao.findPaymentByPayId(user.getPayid());
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
        Payment payment = paymentDao.findPaymentByPayId(payid);
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
        Payment payment = paymentDao.findPaymentByPayId(payid);
        if(!payment.getPassword().equals(oldPassword)){
            return "wrong password";
        }
        paymentDao.updatePasswordByPayid(payid,newPassword);
        return "success";
    }

    @Override
    public String ChangePassword(String userid, String oldPassword, String newPassword) {
        User user = userDao.findUserByUserid(userid);
        if(!user.getPassword().equals(oldPassword)){
            return "wrong password";
        }
        userDao.updatePasswordByUserid(userid,newPassword);
        return "success";
    }

    @Override
    public void WriteOff(String userid) {
        userDao.updateWriteOffByUserId(userid);
    }

    @Override
    public JSONObject GetUserPayMessage(String userid) {
        User user = userDao.findUserByUserid(userid);
        JSONObject json = new JSONObject();
        json.put("level",user.getLevel());
        json.put("points",user.getPoints());
        if(user.getPayid()==null||user.getPayid().equals("")){
            json.put("payid","not");
        }else{
            json.put("payid","has");
        }
        return json;
    }
}
