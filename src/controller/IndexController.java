package controller;

import dao.UserDao;
import dao.UserDaoImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@Controller
public class IndexController {

    private static ApplicationContext applicationContext;
    private static UserService userService;

    public void init(){
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (UserService) applicationContext.getBean("UserService");
        System.out.println("hello");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(ModelMap model){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public String doLogin(@RequestParam(value = "userid")String userid,@RequestParam(value = "password")String password){
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (UserService) applicationContext.getBean("UserService");
        System.out.println(userid+"  "+password);
        System.out.println(userService.getPasswordByUserid("100001"));
        JSONObject json = new JSONObject();
        json.put("result","success");
        String result = json.toString();
        return result;
    }
}
