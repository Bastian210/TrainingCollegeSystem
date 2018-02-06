package service;

import dao.LessonDao;
import dao.OrderDao;
import dao.PlanDao;
import model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PlanDao planDao;

    @Autowired
    private LessonDao lessonDao;

    @Override
    public JSONObject AddOrder(String userid, String lessonid, String institutionid, String type, String price, String actualpay, String classtype, String[] nameList, String[] genderList, String[] educationList) {
        String max_id = orderDao.getMaxId();
        String orderid = String.valueOf(Integer.valueOf(max_id)+1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now= Calendar.getInstance();
        String time = sdf.format(now.getTimeInMillis());
        now.add(Calendar.MINUTE,15);
        String deadline = sdf.format(now.getTimeInMillis());
        String state = "";
        if(type.equals("不选班级")){
            state = "等待配票";
        }else{
            state = "预订完成";
        }

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
        return jsonObject;
    }
}
