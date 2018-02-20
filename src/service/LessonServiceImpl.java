package service;

import dao.InstitutionDao;
import dao.LessonDao;
import dao.PlanDao;
import dao.UserDao;
import model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonDao lessonDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InstitutionDao institutionDao;

    @Autowired
    private PlanDao planDao;

    @Override
    public JSONObject[] GetLessonByUserId(String userid) {
        User user = userDao.findUserByUserid(userid);
        List list = lessonDao.findLessonListByName(user.getUsername());
        return getLessonJsonMessage(list);
    }

    @Override
    public JSONObject[] GetLessonByInstitutionId(String institutionid) {
        return new JSONObject[0];
    }

    @Override
    public void WithdrawLesson(String lessonid,String userid) {
        User user = userDao.findUserByUserid(userid);
        Lesson lesson = lessonDao.findLessonByLessonidAndName(lessonid,user.getUsername());
        lesson.setState("已退课");
        lessonDao.update(lesson);
    }

    @Override
    public void OnSiteBook(String lessonid, String type, String userid, String classtype, String actual, String[] nameList, String[] genderList, String[] educationList) {
        if(type.equals("是")){
            User user = userDao.findUserByUserid(userid);
            user.setPoints((int) (user.getPoints()+Double.valueOf(actual)));
            user.setConsumption(user.getConsumption()+Double.valueOf(actual));
            user.setLevel((int) (user.getConsumption()/1000+1));
            userDao.update(user);
        }

        Plans plans = planDao.getPlanByPlanKey(new PlansKey(lessonid,classtype));
        int sold = plans.getSold();
        for(int i=0;i<nameList.length;i++){
            int id = (sold+1)/plans.getStudentnum()+1;
            lessonDao.save(new Lesson(lessonid,classtype,nameList[i],0,"未开课",String.valueOf(id),genderList[i],educationList[i]));
            sold++;
        }
        plans.setSold(sold);
        planDao.updatePlan(plans);
    }

    @Override
    public JSONObject[] SearchStudents(String lessonid, String classtype, String classid, String classhour) {
        List list = lessonDao.findLessonByLessonidAndClassid(lessonid, classtype, classid);

        JSONObject[] jsonObjects = new JSONObject[list.size()];

        for(int i=0;i<list.size();i++){
            Lesson lesson = (Lesson) list.get(i);
            JSONObject json = new JSONObject();
            json.put("name",lesson.getName());
            json.put("gender",lesson.getGender());
            json.put("education",lesson.getEducation());
            json.put("grade",lesson.getGrade());

            Checkin checkin = lessonDao.findCheckinByKey(new CheckinKey(lessonid,classtype,lesson.getName(),Integer.valueOf(classhour)));
            if(checkin==null){
                json.put("checkin","无");
            }else{
                json.put("checkin","已登记");
            }
            jsonObjects[i] = json;
        }
        return jsonObjects;
    }

    @Override
    public void EnterGrade(String lessonid, String classtype, String name, String grade) {
        Lesson lesson = lessonDao.findLessonByLessonKey(new LessonKey(lessonid,classtype,name));
        lesson.setGrade(Double.valueOf(grade));
        lessonDao.update(lesson);
    }

    @Override
    public void CheckIn(String lessonid, String classtype, String name, String classhour) {
        lessonDao.saveCheckIn(new Checkin(lessonid,classtype,name,Integer.valueOf(classhour), LocalDate.now().toString(),1));
    }

    private JSONObject[] getLessonJsonMessage(List list){
        JSONObject[] jsonObjects = new JSONObject[list.size()];
        for(int i=0;i<list.size();i++){
            Lesson lesson = (Lesson) list.get(i);
            JSONObject json = new JSONObject();
            Plans plans = planDao.getPlanByPlanKey(new PlansKey(lesson.getLessonid(),lesson.getClasstype()));
            json.put("institutionname",institutionDao.findInstitutionById(plans.getInstitutionid()).getInstitutionname());
            json.put("type",plans.getType());
            json.put("lessonname",plans.getLesson());
            json.put("begin",plans.getBegin());
            json.put("end",plans.getEnd());
            json.put("description",plans.getDescription());
            json.put("classtype",lesson.getClasstype()+lesson.getClassid()+"班");
            json.put("teacher",plans.getTeacher());
            json.put("state",lesson.getState());
            json.put("lessonid",lesson.getLessonid());
            double grade = lesson.getGrade();
            if(grade==0){
                json.put("grade","无");
            }else{
                json.put("grade",grade+"分");
            }

            jsonObjects[i] = json;
        }

        return jsonObjects;
    }
}
