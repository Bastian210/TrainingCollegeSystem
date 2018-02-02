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
}
