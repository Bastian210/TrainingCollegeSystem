package dao.impl;

import dao.PlanDao;
import model.Plans;
import model.key.PlansKey;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.util.List;

@Repository
public class PlanDaoImpl implements PlanDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public PlanDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Plans plans) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(plans);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(String lessonid) {
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Plans as p where p.lessonid = '%s' order by p.price", lessonid);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void updateStateByLessonId(String lessonid, String state) {
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Plans plans = session.get(Plans.class,plansKey);
        transaction.commit();
        session.close();
        return plans;
    }

    @Override
    public void updatePlan(Plans plans) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(plans);
        transaction.commit();
        session.close();
    }

    @Override
    public List getAllPlan() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from model.Plans";
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public int getPlanNumByInstitutionId(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("select distinct lessonid from model.Plans as p where p.institutionid = '%s'", id);
        Query query = session.createQuery(hql);
        List list = query.list();
        return list.size();
    }
}
