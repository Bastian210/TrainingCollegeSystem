package service;

import dao.*;
import model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
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
                Lesson lesson = new Lesson(lessonid,classtype,nameList[i],0,"未开课",String.valueOf(id));
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
        JSONObject[] jsonObjects = new JSONObject[list.size()];
        for(int i=0;i<list.size();i++){
            Orders orders = (Orders) list.get(i);
            JSONObject json = new JSONObject();
            json.put("time",orders.getOrdertime().substring(0,10));
            json.put("orderid",orders.getOrderid());
            json.put("institutionname",institutionDao.findInstitutionById(orders.getInstitutionid()).getInstitutionname());
            List list1 = planDao.getPlanByLessonId(orders.getLessonid());
            Plans plans = (Plans) list1.get(0);
            json.put("lessonname",plans.getLesson());
            json.put("type",orders.getType());
            json.put("classtype",orders.getClasstype());
            json.put("num",orders.getNum());
            json.put("price",orders.getPrice());
            json.put("actualpay",orders.getActualpay());
            json.put("state",orders.getState());
            if(orders.getState().equals("未支付")){
                User user = userDao.findUserByUserid(userid);
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
                        Lesson lesson = lessonDao.findLessonByLessonKey(new LessonKey(orders.getLessonid(),orders.getClasstype(),nameList[k]));
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

    @Override
    public void CancelOrder(String orderid) {
        Orders orders = orderDao.findOrderByOrderId(orderid);
        orders.setState("已退订");
        orderDao.update(orders);
    }

    @Override
    public void DeleteOrder(String orderid) {
        orderDao.delete(orderid);
    }
}
