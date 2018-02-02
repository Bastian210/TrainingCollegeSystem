package dao;

import model.Institution;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.util.List;

@Repository
public class InstitutionDaoImpl implements InstitutionDao {
    @Override
    public void save(Institution institution) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(institution);
        transaction.commit();
        session.close();
    }

    @Override
    public Institution findInstitutionById(String id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Institution institution = session.get(Institution.class,id);
        transaction.commit();
        session.close();
        return institution;
    }

    @Override
    public int getMaxId() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from model.Institution";
        Query query = session.createQuery(hql);
        List list = query.list();
        if(list.size()==0){
            return 1000000;
        }
        Institution institution = (Institution) list.get(list.size()-1);
        transaction.commit();
        session.close();
        return Integer.parseInt(institution.getInstitutionid());
    }

    @Override
    public Institution findInstitutionByPhone(String phone) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Institution as ins where ins.phone='%s'", phone);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        if(list.size()==0){
            return null;
        }
        return (Institution) list.get(list.size()-1);
    }
}
