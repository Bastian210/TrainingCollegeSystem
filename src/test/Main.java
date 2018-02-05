package test;

import org.json.JSONObject;
import service.*;

public class Main {
    public static void main(String[] args) {
//        UserService service = new UserSerciveImpl();
//        service.SendCode("2518740156@qq.com");
//        InstitutionService service = new InstitutionServiceImpl();
//        service.Register("机构A","XX省","13270099850","1234");
        PlanService service = new PlanServiceImpl();
        JSONObject json = service.GetPlan("10001");
        System.out.println(json.toString());
        JSONObject[] result = service.GetPlanList("1000001");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",result);
        System.out.println(jsonObject.toString());
    }
}
