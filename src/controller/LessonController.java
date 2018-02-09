package controller;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.LessonService;
import utils.Param;

@Controller
public class LessonController {
    private static ApplicationContext applicationContext;
    private static LessonService lessonService;

    public static ApplicationContext getApplicationContext() {
        if(applicationContext==null){
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return applicationContext;
    }

    public static LessonService getLessonService() {
        if(lessonService==null){
            lessonService = (LessonService) getApplicationContext().getBean("LessonService");
        }
        return lessonService;
    }

    @ResponseBody
    @RequestMapping(value = "/myLesson.getAllUserLesson",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doGetAllUserLesson(){
        JSONObject[] jsonObjects = getLessonService().GetLessonByUserId(Param.getUserid());
        JSONObject json = new JSONObject();
        json.put("result",jsonObjects);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/myLesson.withdrawLesson",method = RequestMethod.POST)
    public String doWithdrawLesson(@RequestParam(value = "lessonid")String lessonid){
        getLessonService().WithdrawLesson(lessonid,Param.getUserid());
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/myLesson.onSiteBook",method = RequestMethod.POST)
    public String doOnSiteBook(@RequestParam(value = "lessonid")String lessonid,@RequestParam(value = "type")String type,@RequestParam(value = "userid")String userid,
                               @RequestParam(value = "classtype")String classtype,@RequestParam(value = "actual")String actual,@RequestParam(value = "nameList[]")String[] nameList,
                               @RequestParam(value = "genderList[]")String[] genderList,@RequestParam(value = "educationList[]")String[] educationList){
        getLessonService().OnSiteBook(lessonid, type, userid, classtype, actual, nameList, genderList, educationList);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/myLesson.searchStudents",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public String doSearchStudents(@RequestParam(value = "lessonid")String lessonid,@RequestParam(value = "classtype")String classtype,@RequestParam(value = "classid")String classid,@RequestParam(value = "classhour")String classhour){
        JSONObject[] jsonObjects = getLessonService().SearchStudents(lessonid, classtype, classid, classhour);
        JSONObject json = new JSONObject();
        json.put("result",jsonObjects);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/myLesson.enterGrade",method = RequestMethod.POST)
    public String doEnterGrade(@RequestParam(value = "lessonid")String lessonid,@RequestParam(value = "classtype")String classtype,@RequestParam(value = "name")String name,@RequestParam(value = "grade")String grade){
        getLessonService().EnterGrade(lessonid, classtype, name, grade);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/myLesson.checkin",method = RequestMethod.POST)
    public String doCheckIn(@RequestParam(value = "lessonid")String lessonid,@RequestParam(value = "classtype")String classtype,@RequestParam(value = "name")String name,@RequestParam(value = "classhour")String classhour){
        getLessonService().CheckIn(lessonid, classtype, name, classhour);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }
}
