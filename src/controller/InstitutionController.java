package controller;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        JSONObject result = getInstitutionService().GetMonthBill(Param.getInstitutionid());
        JSONObject json = new JSONObject();
        json.put("id",map.get("id"));
        json.put("name",map.get("name"));
        json.put("address",map.get("address"));
        json.put("phone",map.get("phone"));
        json.put("payid",map.get("payid"));
        json.put("balance",map.get("balance"));
        json.put("profit",map.get("profit"));
        json.put("month",result.get("month"));
        json.put("profit_array",result.get("profit"));
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

    @ResponseBody
    @RequestMapping(value = "/insManagement.changePassword",method = RequestMethod.POST)
    public String doChangePassword(@RequestParam(value = "oldpassword")String oldpassword,@RequestParam(value = "newpassword")String newpassword){
        String result = getInstitutionService().ChangePassword(Param.getInstitutionid(),oldpassword,newpassword);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insManagement.addTeacher",method = RequestMethod.POST)
    public String doAddTeacher(@RequestParam(value = "name")String name,@RequestParam(value = "gender")String gender,@RequestParam(value = "type")String type){
        String result = getInstitutionService().AddTeacher(Param.getInstitutionid(),name,gender,type);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insManagement.getTeacher",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetTeacher(){
        Map map = getInstitutionService().GetTeacher(Param.getInstitutionid());
        JSONObject json = new JSONObject();
        json.put("index",map.get("index"));
        json.put("name",map.get("name"));
        json.put("gender",map.get("gender"));
        json.put("type",map.get("type"));
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insManagement.changeTeacherMess",method = RequestMethod.POST)
    public String doChangeTeacherMessage(@RequestParam(value = "name")String name,@RequestParam(value = "gender")String gender,@RequestParam(value = "type")String type){
        getInstitutionService().ChangeTeacherMessage(Param.getInstitutionid(),name,gender,type);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/insManagement.deleteTeacher",method = RequestMethod.POST)
    public String doDeleteTeacherMessage(@RequestParam(value = "name")String name){
        getInstitutionService().DeleteTeacher(Param.getInstitutionid(),name);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/institution.getTeacher",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetTeacherByLessonType(@RequestParam(value = "type")String type){
        String[] result = getInstitutionService().GetTeacherName(Param.getInstitutionid(),type);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/amaldar.getRegisterApply",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doRegisterApply(){
        JSONObject[] jsonObjects = getInstitutionService().GetRegisterApply();
        JSONObject json = new JSONObject();
        json.put("result",jsonObjects);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/amaldar.getChangeMessApply",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetChangeMessApply(){
        JSONObject[] jsonObjects = getInstitutionService().GetChangeMessApply();
        JSONObject json = new JSONObject();
        json.put("result",jsonObjects);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/amaldar.refuseRegister",method = RequestMethod.POST)
    public String doRefuseRegister(@RequestParam(value = "id")String id){
        getInstitutionService().RefuseRegister(id);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/amaldar.agreeRegister",method = RequestMethod.POST)
    public String doAgreeRegister(@RequestParam(value = "id")String id){
        getInstitutionService().AgreeRegister(id);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/amaldar.refuseChange",method = RequestMethod.POST)
    public String doRefuseChange(@RequestParam(value = "id")String id){
        getInstitutionService().RefuseChange(id);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/amaldar.agreeChange",method = RequestMethod.POST)
    public String doAgreeChange(@RequestParam(value = "id")String id){
        getInstitutionService().AgreeChange(id);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/statistics.getAllInstitution",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetAllInstitution(){
        JSONObject[] jsonObjects = getInstitutionService().GetAllInstitution();
        JSONObject json = new JSONObject();
        json.put("result",jsonObjects);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/institution.logout",method = RequestMethod.POST)
    public String doInsLogout(){
        Param.setInstitutionid(null);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }
}
