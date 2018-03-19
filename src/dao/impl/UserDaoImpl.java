package dao.impl;

import dao.UserDao;
import model.SecurityCode;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(String userid) {

    }

    @Override
    public User findUserByUserid(String userid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userid);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public User findUserByEmail(String email){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.User as u where u.email = '%s'", email);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        if(list.size()==0){
            return null;
        }else{
            return ((User)list.get(0));
        }
    }

    @Override
    public String getMaxUserid() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from model.User";
        Query query = session.createQuery(hql);
        List list = query.list();
        User user = (User) list.get(list.size()-1);
        transaction.commit();
        session.close();
        return user.getUserid();
    }

    @Override
    public void saveCode(String email, String code){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        SecurityCode securityCode = session.get(SecurityCode.class,email);
        if(securityCode!=null){
            securityCode.setCode(code);
            session.save(securityCode);
        }else{
            securityCode = new SecurityCode(email,code);
            session.save(securityCode);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public String getCode(String email) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        SecurityCode securityCode = session.get(SecurityCode.class,email);
        if(securityCode==null){
            return null;
        }
        transaction.commit();
        session.close();
        return securityCode.getCode();
    }

    @Override
    public void updateMessageByUserId(String userid, String username, String gender, String education) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userid);
        user.setUsername(username);
        user.setGender(gender);
        user.setEducation(education);
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void updatePayIdByUserId(String userid, String payid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userid);
        user.setPayid(payid);
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void updatePasswordByUserid(String userid, String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userid);
        user.setPassword(password);
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateWriteOffByUserId(String userid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userid);
        user.setWriteoff(1);
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public User findManager() {
        return findUserByUserid("500001");
    }

    @Override
    public List findCommonUser() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from model.User as u where u.isadmin = 0";
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }
}
