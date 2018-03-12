package test;

import dao.PlanDao;
import dao.impl.PlanDaoImpl;
import org.json.JSONObject;
import service.OrderService;
import service.impl.OrderServiceImpl;

public class Main {
    public static void main(String[] args) {
        OrderService service = new OrderServiceImpl();
//        JSONObject json = service.GetAllOrder("500002");
//        System.out.println(json);
//        JSONObject json = service.GetOrderMessage("10000007");
//        System.out.println(json);
        PlanDao dao = new PlanDaoImpl();
        int num = dao.getPlanNumByInstitutionId("1000002");
        System.out.println(num);
    }
}
