package dao;

import model.Plans;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
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
}
