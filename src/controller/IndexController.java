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

    private static ApplicationContext getApplicationContext(){
        if(applicationContext==null){
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return applicationContext;
    }

    private static UserService getUserService(){
        if(userService==null){
            userService = (UserService) getApplicationContext().getBean("UserService");
        }
        return userService;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(ModelMap model){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public String doLogin(@RequestParam(value = "userid")String userid,@RequestParam(value = "password")String password){
        System.out.println(userid+"  "+password);
        System.out.println(getUserService().getPasswordByUserid("100001"));
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }
}
