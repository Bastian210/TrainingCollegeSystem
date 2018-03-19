package utils;

import dao.*;
import dao.impl.*;
import model.*;
import model.key.BillKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.impl.OrderServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
@EnableScheduling
public class MyScheduler {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PlanDao planDao;
    @Autowired
    private BillDao billDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private InstitutionDao institutionDao;

    @Scheduled(cron="0/20 * *  * * ? ")   //每20秒执行一次
    public void CheckNotpayOrder(){
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
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void CheckOrder(){
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
                            lessonDao.save(new Lesson(orders.getLessonid(),assign[j][0],ordermessage.getName(),0,"未开课",assign[j][1],ordermessage.getGender(),ordermessage.getEducation(),null,0));
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

    @Scheduled(cron = "0 0 3 * * ?")
    public void CheckPlan() {
        List list = planDao.getAllPlan();
        for(int i=0;i<list.size();i++){
            Plans plans = (Plans) list.get(i);
            String begin = plans.getBegin();
            String end = plans.getEnd();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            Date date1 = null;
            try {
                date = sdf.parse(end);
                date1 = sdf.parse(begin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date now = new Date();
            //如果当前时间已经到了正在售票的课程的开始时间，则将计划状态置为start，将课程状态置为已开课
            if((date1.equals(now)||date1.before(now))&&plans.getState().equals("selling")){
                plans.setState("start");
                planDao.updatePlan(plans);
                lessonDao.updateStateByLessonid(plans.getLessonid(),"已开课");
            }
            //如果当前时间已经到了待评估的课程的开始时间，则将计划状态置为outtime
            if((date1.equals(now)||date1.before(now))&&plans.getState().equals("undetermined")){
                plans.setState("outtime");
                planDao.updatePlan(plans);
            }
            //如果当前时间已经到了已开课的课程的结束时间，则将计划状态置为end，将课程状态置为已结课
            if((date.equals(now)||date.before(now))&&plans.getState().equals("start")){
                plans.setState("end");
                planDao.updatePlan(plans);
                lessonDao.updateStateByLessonid(plans.getLessonid(),"已结课");
            }
        }
    }
}
