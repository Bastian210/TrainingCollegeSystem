package service.impl;

import dao.*;
import dao.impl.*;
import model.*;
import model.key.BillKey;
import model.key.LessonKey;
import model.key.PlansKey;
import org.hibernate.criterion.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PlanDao planDao;

    @Autowired
    private LessonDao lessonDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InstitutionDao institutionDao;

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private BillDao billDao;

    @Override
    public JSONObject AddOrder(String userid, String lessonid, String institutionid, String type, String price, String actualpay, String classtype, String[] nameList, String[] genderList, String[] educationList) {
        //分配orderid
        String max_id = orderDao.getMaxId();
        String orderid = String.valueOf(Integer.valueOf(max_id)+1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now= Calendar.getInstance();
        String time = sdf.format(now.getTimeInMillis());
        now.add(Calendar.MINUTE,3);
        String deadline = sdf.format(now.getTimeInMillis());
        String state = "未支付";

        //保存order信息
        Orders orders = new Orders(orderid,userid,institutionid,lessonid,type,nameList.length,time,Double.valueOf(price),Double.valueOf(actualpay),classtype,deadline,state);
        orderDao.save(orders);

        //保存订单中所有学生的信息
        for(int i=0;i<nameList.length;i++){
            Ordermessage ordermessage = new Ordermessage(orderid,nameList[i],genderList[i],educationList[i]);
            orderDao.saveOrderMessage(ordermessage);
        }

        //如果用户选择了班级，则更新plan中的班级名额剩余情况
        if(type.equals("选班级")){
            Plans plans = planDao.getPlanByPlanKey(new PlansKey(lessonid,classtype));
            int total = plans.getClassnum()*plans.getStudentnum();
            int sold = plans.getSold();
            for(int i=0;i<nameList.length;i++){
                //分配班级
                int id = (sold+1)/plans.getStudentnum()+1;
                Lesson lesson = new Lesson(lessonid,classtype,nameList[i],0,"未开课",String.valueOf(id),genderList[i],educationList[i],null,0);
                lessonDao.save(lesson);
                sold++;
            }
            plans.setSold(sold);
            //如果订单已经分配完
            if(total==sold){
                plans.setState("soldout");
            }
            planDao.updatePlan(plans);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deadline",deadline);
        jsonObject.put("orderid",orderid);
        return jsonObject;
    }

    @Override
    public void UnsubscribeOrder(String orderid) {
        Orders orders = orderDao.findOrderByOrderId(orderid);
        orders.setState("已退订");
        orderDao.update(orders);
        List OrderMessageList = orderDao.getOrderMessageListByOrderId(orderid);
        //删除订单相关用户的课程信息
        for(int i=0;i<OrderMessageList.size();i++){
            Ordermessage ordermessage = (Ordermessage) OrderMessageList.get(i);
            Lesson lesson = lessonDao.findLessonByLessonidAndName(orders.getLessonid(),ordermessage.getName());
            lessonDao.delete(lesson);
        }
    }

    @Override
    public JSONObject GetAllOrder(String userid) {
        List list = orderDao.findOrderListByUserId(userid);
        return getOrderJsonMessage(list);
    }

    @Override
    public JSONObject GetOrderMessage(String orderid) {
        JSONObject json = new JSONObject();
        Orders orders = orderDao.findOrderByOrderId(orderid);

        List orderMessageList = orderDao.getOrderMessageListByOrderId(orderid);
        int length = orderMessageList.size();
        String[] nameList = new String[length];
        String[] genderList = new String[length];
        String[] educationList = new String[length];
        String[] classidList = new String[length];
        for(int k=0;k<length;k++){
            Ordermessage ordermessage = (Ordermessage) orderMessageList.get(k);
            nameList[k] = ordermessage.getName();
            genderList[k] = ordermessage.getGender();
            educationList[k] = ordermessage.getEducation();
            String state = orders.getState();
            if(orders.getType().equals("选班级")){
                if(state.equals("已退订")){
                    classidList[k] = "无";
                }else{
                    classidList[k] = orders.getClasstype()+lessonDao.findLessonByLessonKey(new LessonKey(orders.getLessonid(),orders.getClasstype(),nameList[k])).getClassid()+"班";
                }
            }else{
                if(state.equals("已预订")){
                    Lesson lesson = lessonDao.findLessonByLessonidAndName(orders.getLessonid(),nameList[k]);
                    classidList[k] = lesson.getClasstype()+lesson.getClassid()+"班";
                }else{
                    classidList[k] = "尚未配班";
                }
            }
        }
        json.put("nameList",nameList);
        json.put("genderList",genderList);
        json.put("educationList",educationList);
        json.put("classidList",classidList);
        return json;
    }

    @Override
    public void CancelOrder(String orderid,String price) {
        Orders orders = orderDao.findOrderByOrderId(orderid);
        orders.setState("已退订");
        orderDao.update(orders);
        List OrderMessageList = orderDao.getOrderMessageListByOrderId(orderid);
        //删除订单相关用户的课程信息
        for(int i=0;i<OrderMessageList.size();i++){
            Ordermessage ordermessage = (Ordermessage) OrderMessageList.get(i);
            Lesson lesson = lessonDao.findLessonByLessonidAndName(orders.getLessonid(),ordermessage.getName());
            lessonDao.delete(lesson);
        }

        //更新用户余额
        User user = userDao.findUserByUserid(orders.getUserid());
        Payment payment = paymentDao.findPaymentByPayId(user.getPayid());
        payment.setBalance(payment.getBalance()+Double.valueOf(price));
        paymentDao.update(payment);
        //更新经理账户余额
        Payment manager = paymentDao.getManagePayment();
        payment.setBalance(payment.getBalance()-Double.valueOf(price));
        paymentDao.update(manager);

        String month = orders.getOrdertime().substring(0,7);
        //更新用户月账单
        Bill bill = billDao.getBillByBillKey(new BillKey(orders.getUserid(),month));
        bill.setIncome(bill.getIncome()+Double.valueOf(price));
        billDao.update(bill);
    }

    @Override
    public void DeleteOrder(String orderid) {
        orderDao.delete(orderid);
        orderDao.deleteOrderMessageByOrderId(orderid);
    }

    @Override
    public JSONObject GetAllInsOrder(String institutionid) {
        List list = orderDao.findOrderListByInstitutionId(institutionid);
        return getOrderJsonMessage(list);
    }

    private JSONObject getOrderJsonMessage(List list){
        JSONObject[] jsonObjects = new JSONObject[list.size()];

        //全部订单
        ArrayList<JSONObject> list1 = new ArrayList<>();
        //未支付的订单
        ArrayList<JSONObject> list2 = new ArrayList<>();
        //已预订/等待配票的订单
        ArrayList<JSONObject> list3 = new ArrayList<>();
        //已退订/配票失败的订单
        ArrayList<JSONObject> list4 = new ArrayList<>();
        //已完成的订单
        ArrayList<JSONObject> list5 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Orders orders = (Orders) list.get(i);
            String state = orders.getState();

            User user = userDao.findUserByUserid(orders.getUserid());
            JSONObject json = new JSONObject();
            json.put("time",orders.getOrdertime().substring(0,10));
            json.put("orderid",orders.getOrderid());
            json.put("institutionname",institutionDao.findInstitutionById(orders.getInstitutionid()).getInstitutionname());
            json.put("username",user.getUsername());
            List planList = planDao.getPlanByLessonId(orders.getLessonid());
            Plans plans = (Plans) planList.get(0);
            json.put("lessonname",plans.getLesson());
            json.put("type",orders.getType());
            json.put("classtype",orders.getClasstype());
            json.put("num",orders.getNum());
            json.put("price",orders.getPrice());
            json.put("actualpay",orders.getActualpay());
            json.put("state",state);
            json.put("begintime",plans.getBegin());

            list1.add(json);

            if(state.equals("未支付")){
                int level = user.getLevel();
                if(orders.getPrice()*(1-level/100.0)==orders.getActualpay()){
                    json.put("checkbox","no");
                }else{
                    json.put("checkbox","yes");
                }
                list2.add(json);
            }else if(state.equals("已预订")||state.equals("等待配票")){
                list3.add(json);
            }else if(state.equals("已退订")||state.equals("配票失败")){
                list4.add(json);
            }else{
                list5.add(json);
            }
        }
        JSONObject json = new JSONObject();
        json.put("all",list1.toArray());
        json.put("not_pay",list2.toArray());
        json.put("has_book",list3.toArray());
        json.put("unsubscribe",list4.toArray());
        json.put("finish",list5.toArray());
        return json;
    }
}
