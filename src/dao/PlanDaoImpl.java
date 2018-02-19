package dao;

import model.Plans;
import model.PlansKey;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import service.PlanService;
import utils.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class PlanDaoImpl implements PlanDao {
    @Override
    public void save(Plans plans) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(plans);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(String lessonid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = new StringBuilder().append("from model.Plans as p where p.lessonid = '").append(lessonid).append("'").toString();
        Query query = session.createQuery(hql);
        List list = query.list();
        for(int i=0;i<list.size();i++){
            Plans plans = (Plans) list.get(i);
            if(plans!=null){
                session.delete(plans);
            }
        }
        transaction.commit();
        session.close();
    }

    @Override
    public String getMaxId() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from model.Plans";
        Query query = session.createQuery(hql);
        List list = query.list();
        if(list.size()==0){
            return "10000";
        }
        int max = 0;
        for(int i=0;i<list.size();i++){
            Plans plans = (Plans) list.get(i);
            if(Integer.valueOf(plans.getLessonid())>max){
                max = Integer.valueOf(plans.getLessonid());
            }
        }
        return String.valueOf(max);
    }

    @Override
    public List getPlanListByInstitutionId(String institutionid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Plans as p where p.institutionid = '%s' order by p.begin desc", institutionid);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List getPlanByLessonId(String lessonid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Plans as p where p.lessonid = '%s'", lessonid);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void updateStateByLessonId(String lessonid, String state) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Plans as p where p.lessonid = '%s'", lessonid);
        Query query = session.createQuery(hql);
        List list = query.list();
        for(int i=0;i<list.size();i++){
            Plans plans = (Plans) list.get(i);
            plans.setState(state);
            session.update(plans);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List getLessonByNameAndType(String name, String[] type) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = new StringBuilder().append("from model.Plans as p where p.state = 'selling' and p.lesson like '%").append(name).append("%'").toString();
        if(type!=null){
            for(int i=0;i<type.length;i++){
                if(i==0){
                    hql = hql+"and(type='"+type[i]+"'";
                }else{
                    hql = hql+"or type='"+type[i]+"'";
                }
                if(i==type.length-1){
                    hql = hql+")";
                }
            }
        }
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public Plans getPlanByPlanKey(PlansKey plansKey) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Plans plans = session.get(Plans.class,plansKey);
        transaction.commit();
        session.close();
        return plans;
    }

    @Override
    public void updatePlan(Plans plans) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(plans);
        transaction.commit();
        session.close();
    }

    @Override
    public void checkPlan(){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from model.Plans";
        Query query = session.createQuery(hql);
        List list = query.list();
        LessonDao lessonDao = new LessonDaoImpl();
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
            if((date1.equals(now)||date1.before(now))&&plans.getState().equals("selling")){
                plans.setState("start");
                session.update(plans);
                lessonDao.updateStateByLessonid(plans.getLessonid(),"已开课");
            }
            if((date1.equals(now)||date1.before(now))&&plans.getState().equals("undetermined")){
                plans.setState("outtime");
                session.update(plans);
            }
            if((date.equals(now)||date.before(now))&&plans.getState().equals("start")){
                plans.setState("end");
                session.update(plans);
                lessonDao.updateStateByLessonid(plans.getLessonid(),"已结课");
            }
        }
        transaction.commit();
        session.close();
    }
}
