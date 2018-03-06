package service;

import dao.*;
import model.Institution;
import model.Lesson;
import model.Plans;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Param;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;

    @Autowired
    private LessonDao lessonDao;

    @Autowired
    private InstitutionDao institutionDao;

    @Override
    public void AddPlan(String id, String name, String type, String begin, String end, String classhour, String description,
                        String[] teacherList, String[] typeList, String[] classNumList, String[] stuNumList, String[] priceList) {
        String max = planDao.getMaxId();
        int max_id = Integer.valueOf(max)+1;
        for(int i=0;i<teacherList.length;i++){
            planDao.save(new Plans(String.valueOf(max_id),typeList[i],id,begin,end,Integer.valueOf(classhour),type,name,description,teacherList[i],
                    Integer.valueOf(classNumList[i]),Integer.valueOf(stuNumList[i]),Double.valueOf(priceList[i]),0,"undetermined"));
        }
    }

    @Override
    public void DeletePlan(String lessonid) {
        planDao.delete(lessonid);
    }

    @Override
    public void SaveLessonId(String lessonid) {
        Param.setLessonid(lessonid);
    }

    @Override
    public JSONObject GetPlan(String lessonid) {
        JSONObject json = new JSONObject();
        List list = planDao.getPlanByLessonId(lessonid);
        Plans first = (Plans) list.get(0);
        json.put("lessonid",lessonid);
        json.put("name",first.getLesson());
        json.put("type",first.getType());
        json.put("begin",first.getBegin());
        json.put("end",first.getEnd());
        json.put("classhour",first.getClasshours());
        json.put("description",first.getDescription());
        json.put("institutionid",first.getInstitutionid());
        Institution institution = institutionDao.findInstitutionById(first.getInstitutionid());
        String address = "";
        if(institution!=null){
            address = institution.getAddress();
        }
        json.put("address",address);

        String[] teacherList = new String[list.size()];
        String[] typeList = new String[list.size()];
        String[] classNumList = new String[list.size()];
        String[] stuNumList = new String[list.size()];
        String[] priceList = new String[list.size()];
        String[] leftList = new String[list.size()];
        for(int i=0;i<list.size();i++){
            Plans plans = (Plans) list.get(i);
            teacherList[i] = plans.getTeacher();
            typeList[i] = plans.getClasstype();
            classNumList[i] = String.valueOf(plans.getClassnum());
            stuNumList[i] = String.valueOf(plans.getStudentnum());
            priceList[i] = String.valueOf(plans.getPrice());
            leftList[i] = String.valueOf(plans.getStudentnum()*plans.getClassnum()-plans.getSold());
        }
        json.put("teacherList",teacherList);
        json.put("typeList",typeList);
        json.put("classNumList",classNumList);
        json.put("stuNumList",stuNumList);
        json.put("priceList",priceList);
        json.put("price",getPriceRange(priceList));
        json.put("leftList",leftList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date begin = sdf.parse(first.getBegin());
            Calendar c = Calendar.getInstance();
            c.setTime(begin);
            c.add(Calendar.DATE,-14);
            Date two_week = c.getTime();
            Date now = new Date();
            //比较课程开始两周前的时间和当前时间
            if(now.before(two_week)){
                json.put("is_two_week","true");
            }else{
                json.put("is_two_week","false");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public JSONObject[] GetPlanList(String institutionid) {
        List list = planDao.getPlanListByInstitutionId(institutionid);
        Institution institution = institutionDao.findInstitutionById(institutionid);
        String address = "";
        if(institution!=null){
            address = institution.getAddress();
        }
        List lessonidList = getLessonId(list);
        JSONObject[] jsonObjects = new JSONObject[lessonidList.size()];
        for(int i=0;i<lessonidList.size();i++){
            String lessonid = (String) lessonidList.get(i);
            List newList = getListByLessonId(list,lessonid);
            JSONObject json = new JSONObject();
            Plans first = (Plans) newList.get(0);
            json.put("lessonid",lessonid);
            json.put("name",first.getLesson());
            json.put("type",first.getType());
            json.put("begin",first.getBegin());
            json.put("end",first.getEnd());
            json.put("classhour",first.getClasshours());
            json.put("description",first.getDescription());
            json.put("state",first.getState());
            json.put("address",address);

            String[] teacherList = new String[newList.size()];
            String[] typeList = new String[newList.size()];
            String[] classNumList = new String[newList.size()];
            String[] stuNumList = new String[newList.size()];
            String[] priceList = new String[newList.size()];
            for(int k=0;k<newList.size();k++){
                Plans plans = (Plans) newList.get(k);
                teacherList[k] = plans.getTeacher();
                typeList[k] = plans.getClasstype();
                classNumList[k] = String.valueOf(plans.getClassnum());
                stuNumList[k] = String.valueOf(plans.getStudentnum());
                priceList[k] = String.valueOf(plans.getPrice());
            }
            json.put("price",getPriceRange(priceList));
            json.put("teacherList",teacherList);
            json.put("typeList",typeList);
            json.put("classNumList",classNumList);
            json.put("stuNumList",stuNumList);
            json.put("priceList",priceList);
            jsonObjects[i] = json;
        }
        return jsonObjects;
    }

    @Override
    public void ReleasePlan(String lessonid) {
        planDao.updateStateByLessonId(lessonid,"selling");
    }

    @Override
    public void EditPlan(String lessonid, String institutionid, String name, String type, String begin, String end, String classhour, String description,
                         String[] teacherList, String[] typeList, String[] classNumList, String[] stuNumList, String[] priceList) {
        planDao.delete(lessonid);
        for(int i=0;i<teacherList.length;i++){
            planDao.save(new Plans(String.valueOf(lessonid),typeList[i],institutionid,begin,end,Integer.valueOf(classhour),type,name,description,teacherList[i],
                    Integer.valueOf(classNumList[i]),Integer.valueOf(stuNumList[i]),Double.valueOf(priceList[i]),0,"undetermined"));
        }
    }

    @Override
    public JSONObject[] GetLessonList() {
        String[] subject = {"语文","数学","英语","物理","化学","政治","历史","地理","生物"};
        return SearchLessonList("","高中",subject);
    }

    @Override
    public JSONObject[] SearchLessonList(String lessonName,String school, String[] subject) {
        String[] type = null;
        if(subject!=null){
            type = new String[subject.length];
            for(int i=0;i<subject.length;i++){
                type[i] = school+"/"+subject[i];
            }
        }
        List list = planDao.getLessonByNameAndType(lessonName,type);
        List lessonidList = getLessonId(list);
        JSONObject[] jsonObjects = new JSONObject[lessonidList.size()];
        for(int i=0;i<lessonidList.size();i++){
            String lessonid = (String) lessonidList.get(i);
            List newList = getListByLessonId(list,lessonid);
            JSONObject json = new JSONObject();
            Plans first = (Plans) newList.get(0);
            json.put("lessonid",lessonid);
            json.put("name",first.getLesson());
            json.put("type",first.getType());
            json.put("begin",first.getBegin());
            json.put("end",first.getEnd());
            json.put("classhour",first.getClasshours());
            json.put("description",first.getDescription());
            json.put("state",first.getState());
            Institution institution = institutionDao.findInstitutionById(first.getInstitutionid());
            String address = "";
            if(institution!=null){
                address = institution.getAddress();
            }
            json.put("address",address);

            String[] teacherList = new String[newList.size()];
            String[] typeList = new String[newList.size()];
            String[] classNumList = new String[newList.size()];
            String[] stuNumList = new String[newList.size()];
            String[] priceList = new String[newList.size()];
            for(int k=0;k<newList.size();k++){
                Plans plans = (Plans) newList.get(k);
                teacherList[k] = plans.getTeacher();
                typeList[k] = plans.getClasstype();
                classNumList[k] = String.valueOf(plans.getClassnum());
                stuNumList[k] = String.valueOf(plans.getStudentnum());
                priceList[k] = String.valueOf(plans.getPrice());
            }
            json.put("price",getPriceRange(priceList));
            json.put("teacherList",teacherList);
            json.put("typeList",typeList);
            json.put("classNumList",classNumList);
            json.put("stuNumList",stuNumList);
            json.put("priceList",priceList);
            jsonObjects[i] = json;
        }
        return jsonObjects;
    }

    @Override
    public void CheckPlan() {
        List list = planDao.getAllPlan();
        for(int i=0;i<list.size();i++){
            Plans plans = (Plans) list.get(i);
            String begin = plans.getBegin();
            String end = plans.getEnd();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            Date date1 = null;
            try {
                date = sdf.parse(end);
                date1 = sdf.parse(begin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date now = new Date();
            //如果当前时间已经到了正在售票的课程的开始时间，则将计划状态置为start，将课程状态置为已开课
            if((date1.equals(now)||date1.before(now))&&plans.getState().equals("selling")){
                plans.setState("start");
                planDao.updatePlan(plans);
                lessonDao.updateStateByLessonid(plans.getLessonid(),"已开课");
            }
            //如果当前时间已经到了待评估的课程的开始时间，则将计划状态置为outtime
            if((date1.equals(now)||date1.before(now))&&plans.getState().equals("undetermined")){
                plans.setState("outtime");
                planDao.updatePlan(plans);
            }
            //如果当前时间已经到了已开课的课程的结束时间，则将计划状态置为end，将课程状态置为已结课
            if((date.equals(now)||date.before(now))&&plans.getState().equals("start")){
                plans.setState("end");
                planDao.updatePlan(plans);
                lessonDao.updateStateByLessonid(plans.getLessonid(),"已结课");
            }
        }
    }

    private List getLessonId(List list){
        List result = new ArrayList();
        String lessonid = "";
        for(int i=0;i<list.size();i++){
            Plans plans = (Plans) list.get(i);
            if(!plans.getLessonid().equals(lessonid)){
                result.add(plans.getLessonid());
                lessonid = plans.getLessonid();
            }
        }
        return result;
    }

    private List getListByLessonId(List list, String lessonid){
        List result = new ArrayList();
        boolean find = false;
        for (Object aList : list) {
            Plans plans = (Plans) aList;
            if (plans.getLessonid().equals(lessonid)) {
                find = true;
                result.add(plans);
            } else {
                if (find) {
                    break;
                }
            }
        }
        return result;
    }

    private String getPriceRange(String[] priceList){
        double max = 0;
        double min = Double.parseDouble(priceList[0]);
        for(int i=0;i<priceList.length;i++){
            double price = Double.parseDouble(priceList[i]);
            if(price>max){
                max = price;
            }
            if(price<min){
                min = price;
            }
        }
        if(min==max){
            return String.valueOf(max);
        }
        return min+"~"+max;
    }
}
