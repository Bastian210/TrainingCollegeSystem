package service;

import dao.*;
import model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        now.add(Calendar.MINUTE,15);
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
                Lesson lesson = new Lesson(lessonid,classtype,nameList[i],0,"未开课",String.valueOf(id),genderList[i],educationList[i]);
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
    public void CheckOrder(){
        //检测未支付订单
        List list1 = orderDao.findOrderListByState("未支付");
        for(int i=0;i<list1.size();i++){
            Orders orders = (Orders) list1.get(i);
            String deadline = orders.getDeadline();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = sdf.parse(deadline);
                //如果最晚支付时间已过，则订单状态自动置为已退订
                if(date.before(new Date())){
                    orders.setState("已退订");
                    orderDao.update(orders);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //检测需要自动配票的订单
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
                //当前时间为课程开始两周前，自动配票
                if(date1.equals(new Date())||date1.before(new Date())){
                    List orderMessageList = orderDao.getOrderMessageListByOrderId(orders.getOrderid());
                    //获取配票信息
                    String[][] assign = getAssignMessage(planList,orderMessageList.size(),orders);
                    if(assign==null){//配票失败
                        //更新订单信息
                        orders.setState("配票失败");
                        orderDao.update(orders);

                        double price = orders.getPrice();
                        //更新用户消费总额、等级
                        User user = userDao.findUserByUserid(orders.getUserid());
                        user.setConsumption(user.getConsumption()-price);
                        user.setLevel((int) (user.getConsumption()/1000+1));
                        userDao.update(user);
                        //更新用户账户余额
                        Payment payment = paymentDao.findPaymentByPayId(user.getPayid());
                        payment.setBalance(payment.getBalance()+price);
                        paymentDao.update(payment);
                        //更新经理账户余额
                        Payment manager = paymentDao.getManagePayment();
                        manager.setBalance(manager.getBalance()-price);
                        paymentDao.update(manager);

                        //更新用户月账单
                        String month = orders.getOrdertime().substring(0,7);
                        Bill bill = billDao.getBillByBillKey(new BillKey(orders.getUserid(),month));
                        bill.setIncome(bill.getIncome()-price);
                        billDao.update(bill);
                    }else{//配票成功
                        //保存学生的课程信息
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

        //检测已预订的订单
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
            //如果当前时间已经到了课程开始时间，则将订单状态置为已完成
            if(date.equals(new Date())||date.before(new Date())){
                orders.setState("已完成");
                orderDao.update(orders);

                //经理将费用转给机构
                double actualPay = orders.getActualpay();
                double transfer_account = actualPay*0.9;
                double profit = actualPay-transfer_account;
                //更新机构的总营业额、账户余额
                Institution institution = institutionDao.findInstitutionById(orders.getInstitutionid());
                institution.setConsumption(institution.getConsumption()+transfer_account);
                institutionDao.update(institution);
                Payment payment = paymentDao.findPaymentByPayId(institution.getPayid());
                payment.setBalance(payment.getBalance()+transfer_account);
                paymentDao.update(payment);

                String month = LocalDate.now().toString().substring(0,7);
                //更新机构的月账单
                Bill ins_bill = billDao.getBillByBillKey(new BillKey(institution.getInstitutionid(),month));
                if(ins_bill==null){
                    ins_bill = new Bill(institution.getInstitutionid(),month,transfer_account);
                    billDao.save(ins_bill);
                }else{
                    ins_bill.setIncome(ins_bill.getIncome()+transfer_account);
                    billDao.update(ins_bill);
                }

                //更新经理的总利润额、账户余额
                Payment manager_payment = paymentDao.getManagePayment();
                manager_payment.setBalance(manager_payment.getBalance()-transfer_account);
                paymentDao.update(manager_payment);
                User manager = userDao.findManager();
                manager.setConsumption(manager.getConsumption()+profit);
                userDao.update(manager);

                //更新经理的月账单
                String manager_id = manager.getUserid();
                Bill manager_bill = billDao.getBillByBillKey(new BillKey(manager_id,month));
                if(manager_bill==null){
                    manager_bill = new Bill(manager_id,month,profit);
                    billDao.save(manager_bill);
                }else{
                    manager_bill.setIncome(manager_bill.getIncome()+profit);
                    billDao.update(manager_bill);
                }
            }
        }
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
        for(int k=0;k<num;k++){
            boolean find = false;
            for(int i=0;i<plansList.size();i++){
                Plans plans = (Plans) plansList.get(i);
                int total = plans.getClassnum()*plans.getStudentnum();
                int sold = plans.getSold();
                if(total>sold){
                    //分配的班级
                    result[k][0] = plans.getClasstype();
                    //班级号
                    result[k][1] = String.valueOf((sold+1)/plans.getStudentnum()+1);
                    //更新plan
                    sold++;
                    plans.setSold(sold);
                    planDao.updatePlan(plans);
                    find = true;
                    break;
                }
            }
            if (!find){
                return null;
            }
        }
        return result;
    }
}
