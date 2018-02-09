package service;

import dao.*;
import model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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

    @Override
    public JSONObject AddOrder(String userid, String lessonid, String institutionid, String type, String price, String actualpay, String classtype, String[] nameList, String[] genderList, String[] educationList) {
        String max_id = orderDao.getMaxId();
        String orderid = String.valueOf(Integer.valueOf(max_id)+1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now= Calendar.getInstance();
        String time = sdf.format(now.getTimeInMillis());
        now.add(Calendar.MINUTE,15);
        String deadline = sdf.format(now.getTimeInMillis());
        String state = "未支付";

        Orders orders = new Orders(orderid,userid,institutionid,lessonid,type,nameList.length,time,Double.valueOf(price),Double.valueOf(actualpay),classtype,deadline,state);
        orderDao.save(orders);

        for(int i=0;i<nameList.length;i++){
            Ordermessage ordermessage = new Ordermessage(orderid,nameList[i],genderList[i],educationList[i]);
            orderDao.saveOrderMessage(ordermessage);
        }

        if(type.equals("选班级")){
            Plans plans = planDao.getPlanByPlanKey(new PlansKey(lessonid,classtype));
            int total = plans.getClassnum()*plans.getStudentnum();
            int sold = plans.getSold();
            for(int i=0;i<nameList.length;i++){
                int id = (sold+1)/plans.getStudentnum()+1;
                Lesson lesson = new Lesson(lessonid,classtype,nameList[i],0,"未开课",String.valueOf(id),genderList[i],educationList[i]);
                lessonDao.save(lesson);
                sold++;
            }
            plans.setSold(sold);
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
    public JSONObject[] GetAllOrder(String userid) {
        List list = orderDao.findOrderListByUserId(userid);
        return getOrderJsonMessage(list);
    }

    @Override
    public void CancelOrder(String orderid,String price) {
        Orders orders = orderDao.findOrderByOrderId(orderid);
        orders.setState("已退订");
        orderDao.update(orders);
        List OrderMessageList = orderDao.getOrderMessageListByOrderId(orderid);
        for(int i=0;i<OrderMessageList.size();i++){
            Ordermessage ordermessage = (Ordermessage) OrderMessageList.get(i);
            Lesson lesson = lessonDao.findLessonByLessonidAndName(orders.getLessonid(),ordermessage.getName());
            lessonDao.delete(lesson);
        }

        User user = userDao.findUserByUserid(orders.getUserid());
        Payment payment = paymentDao.findPaymentByPayId(user.getPayid());
        payment.setBalance(payment.getBalance()+Double.valueOf(price));
        paymentDao.update(payment);
        Payment manager = paymentDao.getManagePayment();
        payment.setBalance(payment.getBalance()-Double.valueOf(price));
        paymentDao.update(manager);
    }

    @Override
    public void DeleteOrder(String orderid) {
        orderDao.delete(orderid);
        orderDao.deleteOrderMessageByOrderId(orderid);
    }

    @Override
    public void CheckOrder(){
        List list1 = orderDao.findOrderListByState("未支付");
        for(int i=0;i<list1.size();i++){
            Orders orders = (Orders) list1.get(i);
            String deadline = orders.getDeadline();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = sdf.parse(deadline);
                if(date.before(new Date())){
                    orders.setState("已退订");
                    orderDao.update(orders);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List list2 = orderDao.findOrderListByState("等待配票");
        for(int i=0;i<list2.size();i++){
            Orders orders = (Orders) list2.get(i);
            List planList = planDao.getPlanByLessonId(orders.getLessonid());
            Plans plans = (Plans) planList.get(0);
            String begin = plans.getBegin();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(begin);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE,-14);
                Date date1 = c.getTime();
                if(date1.equals(new Date())||date1.before(new Date())){
                    List orderMessageList = orderDao.getOrderMessageListByOrderId(orders.getOrderid());
                    String[][] assign = getAssignMessage(planList,orderMessageList.size(),orders);
                    if(assign==null){
                        orders.setState("配票失败");
                        orderDao.update(orders);

                        double price = orders.getPrice();
                        User user = userDao.findUserByUserid(orders.getUserid());
                        user.setConsumption(user.getConsumption()-price);
                        user.setLevel((int) (user.getConsumption()/1000+1));
                        userDao.update(user);

                        Payment payment = paymentDao.findPaymentByPayId(user.getPayid());
                        payment.setBalance(payment.getBalance()+price);
                        paymentDao.update(payment);
                        Payment manager = paymentDao.getManagePayment();
                        manager.setBalance(manager.getBalance()-price);
                        paymentDao.update(manager);
                    }else{
                        for(int j=0;j<orderMessageList.size();j++){
                            Ordermessage ordermessage = (Ordermessage) orderMessageList.get(j);
                            lessonDao.save(new Lesson(orders.getLessonid(),assign[j][0],ordermessage.getName(),0,"未开课",assign[j][1],ordermessage.getGender(),ordermessage.getEducation()));
                        }
                        orders.setState("已预订");
                        orderDao.update(orders);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List list3 = orderDao.findOrderListByState("已预订");
        for(int i=0;i<list3.size();i++){
            Orders orders = (Orders) list3.get(i);
            List planList = planDao.getPlanByLessonId(orders.getLessonid());
            Plans plans = (Plans) planList.get(0);
            String begin = plans.getBegin();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(begin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(date.equals(new Date())||date.before(new Date())){
                orders.setState("已完成");
                orderDao.update(orders);

                double actualPay = orders.getActualpay();
                Institution institution = institutionDao.findInstitutionById(orders.getInstitutionid());
                institution.setConsumption(institution.getConsumption()+actualPay*0.9);
                Payment payment = paymentDao.findPaymentByPayId(institution.getPayid());
                payment.setBalance(payment.getBalance()+actualPay*0.9);
                paymentDao.update(payment);
                Payment manage = paymentDao.getManagePayment();
                manage.setBalance(manage.getBalance()-actualPay*0.9);
                paymentDao.update(manage);
                User manager = userDao.findManager();
                manager.setConsumption(manager.getConsumption()+actualPay*0.1);
                userDao.update(manager);
            }
        }

        planDao.checkPlan();
    }

    @Override
    public JSONObject[] GetAllInsOrder(String institutionid) {
        List list = orderDao.findOrderListByInstitutionId(institutionid);
        return getOrderJsonMessage(list);
    }

    private JSONObject[] getOrderJsonMessage(List list){
        JSONObject[] jsonObjects = new JSONObject[list.size()];
        for(int i=0;i<list.size();i++){
            Orders orders = (Orders) list.get(i);
            JSONObject json = new JSONObject();
            json.put("time",orders.getOrdertime().substring(0,10));
            json.put("orderid",orders.getOrderid());
            json.put("institutionname",institutionDao.findInstitutionById(orders.getInstitutionid()).getInstitutionname());
            json.put("username",userDao.findUserByUserid(orders.getUserid()).getUsername());
            List list1 = planDao.getPlanByLessonId(orders.getLessonid());
            Plans plans = (Plans) list1.get(0);
            json.put("lessonname",plans.getLesson());
            json.put("type",orders.getType());
            json.put("classtype",orders.getClasstype());
            json.put("num",orders.getNum());
            json.put("price",orders.getPrice());
            json.put("actualpay",orders.getActualpay());
            json.put("state",orders.getState());
            json.put("begintime",plans.getBegin());
            if(orders.getState().equals("未支付")){
                User user = userDao.findUserByUserid(orders.getUserid());
                int level = user.getLevel();
                if(orders.getPrice()*(1-level/100.0)==orders.getActualpay()){
                    json.put("checkbox","no");
                }else{
                    json.put("checkbox","yes");
                }
            }

            List orderMessageList = orderDao.getOrderMessageListByOrderId(orders.getOrderid());
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
                if(orders.getType().equals("选班级")){
                    classidList[k] = orders.getClasstype()+lessonDao.findLessonByLessonKey(new LessonKey(orders.getLessonid(),orders.getClasstype(),nameList[k])).getClassid()+"班";
                }else{
                    if(orders.getState().equals("已预订")){
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

            jsonObjects[i] = json;
        }
        return jsonObjects;
    }

    /**
     * 配票
     * @param plansList
     * @param num
     * @param orders
     * @return
     */
    private String[][] getAssignMessage(List plansList,int num,Orders orders){
        String[][] result = new String[num][2];
        double price = 0;
        for(int k=0;k<num;k++){
            boolean find = false;
            for(int i=0;i<plansList.size();i++){
                Plans plans = (Plans) plansList.get(i);
                int total = plans.getClassnum()*plans.getStudentnum();
                int sold = plans.getSold();
                if(total>sold){
                    result[k][0] = plans.getClasstype();
                    result[k][1] = String.valueOf((sold+1)/plans.getStudentnum()+1);
                    sold++;
                    plans.setSold(sold);
                    planDao.updatePlan(plans);
                    find = true;
                    price = price+plans.getPrice();
                    break;
                }
            }
            if (!find){
                return null;
            }
        }

        //退去部分款项
        double past_price = orders.getPrice();
        double past_actual = orders.getActualpay();

        User user = userDao.findUserByUserid(orders.getUserid());
        if(price!=past_price){
            double actual = price*(1-user.getLevel()/100.0);
            double change = past_actual-actual;

            Payment payment = paymentDao.findPaymentByPayId(user.getPayid());
            payment.setBalance(payment.getBalance()+change);
            paymentDao.update(payment);
            Payment manager = paymentDao.getManagePayment();
            manager.setBalance(manager.getBalance()-change);
            paymentDao.update(manager);
        }
        return result;
    }
}
