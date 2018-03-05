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

    @ResponseBody
    @RequestMapping(value = "/institution.deletePlan",method = RequestMethod.POST)
    public String doDeletePlan(@RequestParam(value = "lessonid")String lessonid){
        getPlanService().DeletePlan(lessonid);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/institution.getPlanList",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetPlanList(){
        JSONObject[] jsonObject = getPlanService().GetPlanList(Param.getInstitutionid());
        JSONObject json = new JSONObject();
        json.put("result",jsonObject);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/institution.getPlan",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetPlan(){
        JSONObject json = getPlanService().GetPlan(Param.getLessonid());
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/institution.saveLessonid",method = RequestMethod.POST)
    public String doSaveLessonId(@RequestParam(value = "lessonid")String lessonid){
        getPlanService().SaveLessonId(lessonid);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/institution.releasePlan",method = RequestMethod.POST)
    public String doReleasePlan(@RequestParam(value = "lessonid")String lessonid){
        getPlanService().ReleasePlan(lessonid);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/editPlan.editPlan",method = RequestMethod.POST)
    public String doEditPlan(@RequestParam(value = "lessonid")String lessonid,@RequestParam(value = "name")String name, @RequestParam(value = "type")String type,@RequestParam(value = "begin")String begin,
                            @RequestParam(value = "end")String end, @RequestParam(value = "classhour")String classhour,@RequestParam(value = "description")String description,
                            @RequestParam(value = "teacherList[]")String[] teacherList,@RequestParam(value = "typeList[]")String[] typeList,@RequestParam(value = "classNumList[]")String[] classNumList,
                            @RequestParam(value = "stuNumList[]")String[] stuNumList,@RequestParam(value = "priceList[]")String[] priceList){
        getPlanService().EditPlan(lessonid,Param.getInstitutionid(),name,type,begin,end,classhour,description,teacherList,typeList,classNumList,stuNumList,priceList);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/index.getLessonList",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetLessonList(){
        JSONObject[] result = getPlanService().GetLessonList();
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/index.searchLessonList",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doSearchLessonList(@RequestParam(value = "lessonname")String lessonname,@RequestParam(value = "school")String school,@RequestParam(value = "subject[]")String[] subject){
        JSONObject[] result = getPlanService().SearchLessonList(lessonname,school,subject);
        JSONObject json = new JSONObject();
        json.put("result",result);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/amaldar.checkPlan",method = RequestMethod.POST)
    public String doCheckPlan(){
        getPlanService().CheckPlan();
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }
}
