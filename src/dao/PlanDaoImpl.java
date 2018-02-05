package dao;

import model.Plans;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import service.PlanService;
import utils.HibernateUtil;

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
        Plans plans = (Plans) list.get(list.size()-1);
        return plans.getLessonid();
    }

    @Override
    public List getPlanListByInstitutionId(String institutionid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Plans as p where p.institutionid = '%s'", institutionid);
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
}
