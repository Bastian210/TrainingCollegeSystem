package controller;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.OrderService;
import service.UserService;
import utils.Param;

@Controller
public class OrderController {

    private static ApplicationContext applicationContext;

    private static OrderService orderService;

    private static UserService userService;

    private static ApplicationContext getApplicationContext(){
        if(applicationContext==null){
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return applicationContext;
    }

    private static OrderService getOrderService(){
        if(orderService==null){
            orderService = (OrderService) getApplicationContext().getBean("OrderService");
        }
        return orderService;
    }

    private static UserService getUserService(){
        if(userService==null){
            userService = (UserService)getApplicationContext().getBean("UserService");
        }
        return userService;
    }

    @ResponseBody
    @RequestMapping(value = "/book.addOrder",method = RequestMethod.POST)
    public String doAddOrder(@RequestParam(value = "lessonid")String lessonid,@RequestParam(value = "institutionid")String institutionid,@RequestParam(value = "type")String type,
                             @RequestParam(value = "price")String price,@RequestParam(value = "actualpay")String actualpay,@RequestParam(value = "classtype")String classtype,
                             @RequestParam(value = "nameList[]")String[] nameList,@RequestParam(value = "genderList[]")String[] genderList,@RequestParam(value = "educationList[]")String[] educationList){
        JSONObject json = getOrderService().AddOrder(Param.getUserid(),lessonid,institutionid,type,price,actualpay,classtype,nameList,genderList,educationList);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/book.payOrder",method = RequestMethod.POST)
    public String doPayOrder(@RequestParam(value = "price")String price,@RequestParam(value = "password")String password,@RequestParam(value = "orderid")String orderid,@RequestParam(value = "checkbox")String checkbox){
        String result = getUserService().Pay(Param.getUserid(),password,price,orderid,checkbox);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/myOrder.getAllOrder",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetAllOrder(){
        JSONObject[] jsonObjects = getOrderService().GetAllOrder("500002");
        JSONObject json = new JSONObject();
        json.put("result",jsonObjects);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/myOrder.cancelOrder",method = RequestMethod.POST)
    public String doCancelOrder(@RequestParam(value = "orderid")String orderid,@RequestParam(value = "price")String price){
        getOrderService().CancelOrder(orderid,price);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/myOrder.deleteOrder",method = RequestMethod.POST)
    public String doDeleteOrder(@RequestParam(value = "orderid")String orderid){
        getOrderService().DeleteOrder(orderid);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insOrder.getAllInsOrder",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String GetAllInsOrder(){
        JSONObject[] jsonObjects = getOrderService().GetAllInsOrder(Param.getInstitutionid());
        JSONObject json = new JSONObject();
        json.put("result",jsonObjects);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/amaldar.checkOrder",method = RequestMethod.POST)
    public String CheckOrder(){
        getOrderService().CheckOrder();
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }
}
