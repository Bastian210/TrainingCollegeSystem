package controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
        JSONObject result = getUserService().GetMonthBill(Param.getUserid());

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
        json.put("month",result.get("month"));
        json.put("consume",result.get("consume"));

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

    @ResponseBody
    @RequestMapping(value = "/statistics.getAllUser",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetAllUser(){
        JSONObject[] jsonObjects = getUserService().GetAllUser();
        JSONObject json = new JSONObject();
        json.put("result",jsonObjects);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/statistics.getProfit",method = RequestMethod.POST)
    public String doGetProfit(){
        double profit = getUserService().getProfit();
        JSONObject result = getUserService().GetMonthBill("500001");
        JSONObject json = new JSONObject();
        json.put("result",profit);
        json.put("month",result.get("month"));
        json.put("profit",result.get("consume"));
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/user.logout",method = RequestMethod.POST)
    public String doLogout(){
        Param.setUserid(null);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/manager.logout",method = RequestMethod.POST)
    public String doInsLogout(){
        Param.setManagerid(null);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }
}
