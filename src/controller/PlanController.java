package controller;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.PlanService;
import utils.Param;

@Controller
public class PlanController {
    private static ApplicationContext applicationContext;
    private static PlanService planService;

    private static ApplicationContext getApplicationContext(){
        if(applicationContext==null){
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return applicationContext;
    }

    public static PlanService getPlanService() {
        if(planService==null){
            planService = (PlanService) getApplicationContext().getBean("PlanService");
        }
        return planService;
    }

    @ResponseBody
    @RequestMapping(value = "/institution.addPlan",method = RequestMethod.POST)
    public String doAddPlan(@RequestParam(value = "name")String name, @RequestParam(value = "type")String type,@RequestParam(value = "begin")String begin,
                            @RequestParam(value = "end")String end, @RequestParam(value = "classhour")String classhour,@RequestParam(value = "description")String description,
                            @RequestParam(value = "teacherList[]")String[] teacherList,@RequestParam(value = "typeList[]")String[] typeList,@RequestParam(value = "classNumList[]")String[] classNumList,
                            @RequestParam(value = "stuNumList[]")String[] stuNumList,@RequestParam(value = "priceList[]")String[] priceList){
        getPlanService().AddPlan(Param.getInstitutionid(),name,type,begin,end,classhour,description,teacherList,typeList,classNumList,stuNumList,priceList);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }
}
