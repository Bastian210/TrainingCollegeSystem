package controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

@Controller
public class UserController {

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

    @ResponseBody
    @RequestMapping(value = "/login.user",method = RequestMethod.POST)
    public String doLogin(@RequestParam(value = "email")String email, @RequestParam(value = "password")String password){
        String result = getUserService().Login(email,password);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/login.manager",method = RequestMethod.POST)
    public String doManagerLogin(@RequestParam(name = "id")String id,@RequestParam(name = "password")String password){
        String result = getUserService().ManagerLogin(id,password);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/register.getCode",method = RequestMethod.POST)
    public String doGetCode(@RequestParam(value = "email")String email){
        getUserService().SendCode(email);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/register.user",method = RequestMethod.POST)
    public String doRegister(@RequestParam(value = "username")String username, @RequestParam(value = "email")String email,@RequestParam(value = "password")String password,@RequestParam(name = "code")String code){
        String result = getUserService().Register(username,email,password,code);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }
}
