package service;

import dao.InstitutionDao;
import dao.LessonDao;
import dao.PlanDao;
import dao.UserDao;
import model.Lesson;
import model.Plans;
import model.PlansKey;
import model.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void WithdrawLesson(String lessonid,String userid) {
        User user = userDao.findUserByUserid(userid);
        Lesson lesson = lessonDao.findLessonByLessonidAndName(lessonid,user.getUsername());
        lesson.setState("已退课");
        lessonDao.update(lesson);
    }

    private JSONObject[] getLessonJsonMessage(List list){
        JSONObject[] jsonObjects = new JSONObject[list.size()];
        for(int i=0;i<list.size();i++){
            Lesson lesson = (Lesson) list.get(i);
            JSONObject json = new JSONObject();
            Plans plans = planDao.getPlanByPlanKey(new PlansKey(lesson.getLessonid(),lesson.getClasstype()));
            json.put("institutionname",institutionDao.findInstitutionById(plans.getInstitutionid()));
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
