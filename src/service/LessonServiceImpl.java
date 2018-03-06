package service;

import dao.*;
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

    @Autowired
    private BillDao billDao;

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
    public int[] OnSiteBook(String lessonid, String type, String userid, String classtype, String actual, String[] nameList, String[] genderList, String[] educationList) {
        Plans plans = planDao.getPlanByPlanKey(new PlansKey(lessonid,classtype));
        double total_price = Double.valueOf(actual);

        //如果订票者是本系统的会员
        if(type.equals("是")){
            //更新用户的积分、消费总额和等级
            User user = userDao.findUserByUserid(userid);
            user.setPoints((int) (user.getPoints()+total_price));
            user.setConsumption(user.getConsumption()+total_price);
            user.setLevel((int) (user.getConsumption()/1000+1));
            userDao.update(user);

            //更新用户的月账单
            String month = LocalDate.now().toString().substring(0,7);
            Bill bill = billDao.getBillByBillKey(new BillKey(userid,month));
            if(bill==null){
                bill = new Bill(userid,month,total_price);
                billDao.save(bill);
            }else{
                bill.setIncome(bill.getIncome()+total_price);
                billDao.update(bill);
            }

            //更新机构的月账单
            Bill ins_bill = billDao.getBillByBillKey(new BillKey(plans.getInstitutionid(),month));
            if(ins_bill==null){
                ins_bill = new Bill(plans.getInstitutionid(),month,total_price);
                billDao.save(ins_bill);
            }else{
                ins_bill.setIncome(ins_bill.getIncome()+total_price);
                billDao.update(ins_bill);
            }
        }

        int sold = plans.getSold();
        int[] classList = new int[nameList.length];

        //分配班级
        for(int i=0;i<nameList.length;i++){
            int id = (sold+1)/plans.getStudentnum()+1;
            classList[i] = id;
            lessonDao.save(new Lesson(lessonid,classtype,nameList[i],0,"未开课",String.valueOf(id),genderList[i],educationList[i],null,0));
            sold++;
        }
        plans.setSold(sold);
        planDao.updatePlan(plans);

        return classList;
    }

    @Override
    public JSONObject[] SearchStudents(String lessonid, String classtype, String classid) {
        List list = lessonDao.findLessonByLessonidAndClassid(lessonid, classtype, classid);

        JSONObject[] jsonObjects = new JSONObject[list.size()];

        for(int i=0;i<list.size();i++){
            Lesson lesson = (Lesson) list.get(i);
            JSONObject json = new JSONObject();
            json.put("name",lesson.getName());
            json.put("gender",lesson.getGender());
            json.put("education",lesson.getEducation());
            json.put("grade",lesson.getGrade());

            int checkin = lesson.getCheckin();
            if(checkin==0){
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
    public void CheckIn(String lessonid, String classtype, String name) {
        Lesson lesson = lessonDao.findLessonByLessonKey(new LessonKey(lessonid,classtype,name));
        lesson.setTime(LocalDate.now().toString());
        lesson.setCheckin(1);
        lessonDao.update(lesson);
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
