package dao.impl;

import dao.InstitutionDao;
import model.Institution;
import model.Teachers;
import model.key.TeachersKey;
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
    public void update(Institution institution) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(institution);
        transaction.commit();
        session.close();
    }

    @Override
    public Institution findInstitutionById(String id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        if(id==null){
            return null;
        }
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

    @Override
    public void updateChanMessById(String id, String chanMess) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Institution institution = session.get(Institution.class,id);
        institution.setChanMess(chanMess);
        session.update(institution);
        transaction.commit();
        session.close();
    }

    @Override
    public void updatePayIdById(String id, String payid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Institution institution = session.get(Institution.class,id);
        institution.setPayid(payid);
        session.update(institution);
        transaction.commit();
        session.close();
    }

    @Override
    public void updatePasswordById(String id, String password) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Institution institution = session.get(Institution.class,id);
        institution.setPassword(password);
        session.update(institution);
        transaction.commit();
        session.close();
    }

    @Override
    public Teachers findTeacherByTeacherKey(TeachersKey key) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Teachers teachers = session.get(Teachers.class,key);
        transaction.commit();
        session.close();
        return teachers;
    }

    @Override
    public void saveTeacher(Teachers teachers) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(teachers);
        transaction.commit();
        session.close();
    }

    @Override
    public List findTeachersById(String id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Teachers as t where t.institutionid='%s'", id);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void updateTeacherMessageByTeachersKey(TeachersKey key, String gender, String type) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Teachers teachers = session.get(Teachers.class,key);
        teachers.setGender(gender);
        teachers.setType(type);
        session.update(teachers);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteTeacher(TeachersKey key) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Teachers teachers = session.get(Teachers.class,key);
        if(teachers!=null){
            session.delete(teachers);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List findTeacherByIdAndType(String id, String type) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Teachers as t where t.institutionid = '%s' and t.type='%s'", id, type);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List findInstitutionByState(String state) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Institution as i where i.state = '%s'", state);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List findInstitutionByChanMess() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from model.Institution as i where i.chanMess !=null";
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }
}
