package service;

import dao.*;
import model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MailUtil;
import utils.Param;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private LessonDao lessonDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BillDao billDao;

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
        map.put("level", String.valueOf(user.getLevel()));
        map.put("points", String.valueOf(user.getPoints()));
        map.put("consumption",String.valueOf(user.getConsumption()));
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

    @Override
    public String Pay(String userid, String password, String price,String orderid,String checkbox) {
        double num = Double.valueOf(price);
        User user = userDao.findUserByUserid(userid);
        Payment payment = paymentDao.findPaymentByPayId(user.getPayid());
        //检验支付密码和账户余额
        if(!payment.getPassword().equals(password)){
            return "wrong password";
        }else if(payment.getBalance()<num){
            return "not enough";
        }
        Orders orders = orderDao.findOrderByOrderId(orderid);
        String deadline = orders.getDeadline();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(deadline);
            //检验支付时间是否已过
            if(date.before(new Date())){
                orders.setState("已退订");
                orderDao.update(orders);
                return "has end";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //更新用户账户余额
        payment.setBalance(payment.getBalance()-num);
        paymentDao.update(payment);
        //更新经理账户余额
        Payment manager = paymentDao.getManagePayment();
        manager.setBalance(manager.getBalance()+num);
        paymentDao.update(manager);
        //更新订单状态
        if(orders.getType().equals("不选班级")){
            orders.setState("等待配票");
        }else{
            orders.setState("已预订");
        }
        orderDao.update(orders);

        //更新积分、消费总额和用户等级
        if(checkbox.equals("yes")){
            user.setPoints((int) num);
        }else{
            user.setPoints((int) (user.getPoints()+num));
        }
        user.setConsumption(user.getConsumption()+num);
        user.setLevel((int) (user.getConsumption()/1000+1));
        userDao.update(user);

        String current_month = LocalDate.now().toString().substring(0,7);
        Bill bill = billDao.getBillByBillKey(new BillKey(userid,current_month));
        //检测时候存在当前用户此月份的消费记录
        if(bill==null){
            bill = new Bill(userid,current_month,num);
            billDao.save(bill);
        }else{
            //更新月消费记录
            bill.setIncome(bill.getIncome()+num);
            billDao.update(bill);
        }
        return "success";
    }

    @Override
    public String TestVip(String userid) {
        User user = userDao.findUserByUserid(userid);
        if(user==null){
            return "wrong id";
        }
        return String.valueOf(user.getLevel());
    }

    @Override
    public JSONObject[] GetAllUser() {
        List list = userDao.findCommonUser();
        JSONObject[] jsonObjects = new JSONObject[list.size()];
        for(int i=0;i<list.size();i++){
            User user = (User) list.get(i);
            JSONObject json = new JSONObject();
            json.put("id",user.getUserid());
            json.put("name",user.getUsername());
            json.put("email",user.getEmail());
            json.put("gender",user.getGender());
            json.put("education",user.getEducation());
            json.put("level",user.getLevel());
            json.put("points",user.getPoints());
            json.put("consumption",user.getConsumption());

            List ordersList = orderDao.findOrderListByUserId(user.getUserid());
            json.put("order",ordersList.size());
            List lessonList = lessonDao.findLessonListByName(user.getUsername());
            json.put("lesson",lessonList.size());

            jsonObjects[i] = json;
        }
        return jsonObjects;
    }

    @Override
    public double getProfit() {
        return userDao.findManager().getConsumption();
    }

    @Override
    public JSONObject GetMonthBill(String userid) {
        JSONObject json = new JSONObject();
        List list = billDao.getBillListByUserid(userid);
        String[] month_list = new String[list.size()];
        double[] consumption_list = new double[list.size()];
        for(int i=0;i<list.size();i++){
            Bill bill = (Bill) list.get(i);
            month_list[i] = bill.getMonth();
            consumption_list[i] = bill.getIncome();
        }
        json.put("month",month_list);
        json.put("consume",consumption_list);
        return json;
    }


}
