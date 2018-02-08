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
import utils.Param;

import java.util.Map;

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

    @ResponseBody
    @RequestMapping(value = "/accountManagement.changeMessage",method = RequestMethod.POST)
    public String doChangeMessage(@RequestParam(value = "userid")String userid, @RequestParam(value = "username")String username, @RequestParam(value = "gender")String gender, @RequestParam(value = "education")String education){
        getUserService().ChangeUserMessage(userid,username,gender,education);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/accountManagement.getMessage",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    public String doGetMessage(){
        Map map = getUserService().GetUserMessage(Param.getUserid());
        JSONObject json = new JSONObject();
        json.put("userid",Param.getUserid());
        json.put("username",map.get("username"));
        json.put("gender",map.get("gender"));
        json.put("education",map.get("education"));
        json.put("payid",map.get("payid"));
        json.put("balance",map.get("balance"));
        json.put("level",map.get("level"));
        json.put("points",map.get("points"));
        json.put("consumption",map.get("consumption"));
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/accountManagement.bindAccount",method = RequestMethod.POST)
    public String doBindAccount(@RequestParam(value = "payid")String payid, @RequestParam(value = "password")String password){
        String result = getUserService().BindAccount(Param.getUserid(),payid,password);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/accountManagement.unbindAccount",method = RequestMethod.POST)
    public String doUnbindAccount(@RequestParam(value = "payid")String payid){
        getUserService().UnbindAccount(Param.getUserid(),payid);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/accountManagement.changePayPwd",method = RequestMethod.POST)
    public String doChangePayPwd(@RequestParam(value = "payid")String payid,@RequestParam(value = "oldPwd")String oldPwd,@RequestParam(value = "newPwd")String newPwd){
        String result = getUserService().ChangePaymentPassword(payid, oldPwd, newPwd);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/accountManagement.changePassword",method = RequestMethod.POST)
    public String doChangePassowrd(@RequestParam(value = "oldpassword")String oldPassword,@RequestParam(value = "newpassword")String newPassword){
        String result = getUserService().ChangePassword(Param.getUserid(),oldPassword,newPassword);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/accountManagement.writeOff",method = RequestMethod.POST)
    public String doWriteOff(){
        getUserService().WriteOff(Param.getUserid());
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/book.getUserPayMessage",method = RequestMethod.POST)
    public String doGetUserPayMessage(){
        JSONObject json = getUserService().GetUserPayMessage(Param.getUserid());
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insLesson.testVip",method = RequestMethod.POST)
    public String doTestVip(@RequestParam(value = "userid")String userid){
        String result = getUserService().TestVip(userid);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }
}
