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

    @ResponseBody
    @RequestMapping(value = "/book.addOrder",method = RequestMethod.POST)
    public String doAddOrder(@RequestParam(value = "lessonid")String lessonid,@RequestParam(value = "institutionid")String institutionid,@RequestParam(value = "type")String type,
                             @RequestParam(value = "price")String price,@RequestParam(value = "actualpay")String actualpay,@RequestParam(value = "classtype")String classtype,
                             @RequestParam(value = "nameList[]")String[] nameList,@RequestParam(value = "genderList[]")String[] genderList,@RequestParam(value = "educationList[]")String[] educationList){
        JSONObject json = getOrderService().AddOrder(Param.getUserid(),lessonid,institutionid,type,price,actualpay,classtype,nameList,genderList,educationList);
        return json.toString();
    }
}
