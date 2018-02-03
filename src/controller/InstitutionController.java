package controller;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.InstitutionService;
import utils.Param;

import java.util.Map;

@Controller
public class InstitutionController {
    private static ApplicationContext applicationContext;
    private static InstitutionService institutionService;

    private static ApplicationContext getApplicationContext(){
        if(applicationContext==null){
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return applicationContext;
    }

    private static InstitutionService getInstitutionService(){
        if(institutionService==null){
            institutionService = (InstitutionService) getApplicationContext().getBean("InstitutionService");
        }
        return institutionService;
    }

    @ResponseBody
    @RequestMapping(value = "/login.institution",method = RequestMethod.POST)
    public String doLogin(@RequestParam(name = "id")String id, @RequestParam(name = "password")String password){
        String result = getInstitutionService().Login(id, password);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/register.institution",method = RequestMethod.POST)
    public String doRegister(@RequestParam(name = "name")String name,@RequestParam(name = "address")String address,@RequestParam(name = "phone")String phone,@RequestParam(name = "password")String password){
        String result = getInstitutionService().Register(name,address,phone,password);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insManagement.getInsMess",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetInsMess(){
        Map map = getInstitutionService().GetInsMess(Param.getInstitutionid());
        JSONObject json = new JSONObject();
        json.put("id",map.get("id"));
        json.put("name",map.get("name"));
        json.put("address",map.get("address"));
        json.put("phone",map.get("phone"));
        json.put("payid",map.get("payid"));
        json.put("balance",map.get("balance"));
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insManagement.changeInsMess",method = RequestMethod.POST)
    public String doChangeInsMess(@RequestParam(value = "name")String name,@RequestParam(name = "address")String address,@RequestParam(name = "phone")String phone){
        getInstitutionService().ChangeInsMess(Param.getInstitutionid(),name,address,phone);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insManagement.bindAccount",method = RequestMethod.POST)
    public String doBindAccount(@RequestParam(value = "payid")String payid, @RequestParam(value = "password")String password){
        String result = getInstitutionService().BindAccount(Param.getInstitutionid(),payid,password);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insManagement.unbindAccount",method = RequestMethod.POST)
    public String doUnbindAccount(@RequestParam(value = "payid")String payid){
        getInstitutionService().UnbindAccount(Param.getInstitutionid(),payid);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insManagement.changePayPwd",method = RequestMethod.POST)
    public String doChangePayPwd(@RequestParam(value = "payid")String payid,@RequestParam(value = "oldPwd")String oldPwd,@RequestParam(value = "newPwd")String newPwd){
        String result = getInstitutionService().ChangePaymentPassword(payid, oldPwd, newPwd);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }
}
