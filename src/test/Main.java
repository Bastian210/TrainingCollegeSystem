package test;

import org.json.JSONObject;
import service.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
//        UserService service = new UserSerciveImpl();
//        service.SendCode("2518740156@qq.com");
//        InstitutionService service = new InstitutionServiceImpl();
//        service.Register("机构A","XX省","13270099850","1234");
//        PlanService service = new PlanServiceImpl();
//        JSONObject json = service.GetPlan("10001");
//        System.out.println(json.toString());
//        JSONObject[] result = service.GetLessonList();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("result",result);
//        System.out.println(jsonObject.toString());
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        Date now = new Date();
//        System.out.println(df.format(now));// new Date()为获取当前系统时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now= Calendar.getInstance();
        String time = sdf.format(now.getTimeInMillis());
        System.out.println(time);
        now.add(Calendar.MINUTE,15);
        String deadline = sdf.format(now.getTimeInMillis());
        System.out.println(deadline);
    }
}
