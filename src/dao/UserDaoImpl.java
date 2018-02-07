package dao;

import model.SecurityCode;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public void save(User user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateUtil.getSession();
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
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userid);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public User findUserByEmail(String email){
        Session session = HibernateUtil.getSession();
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
        Session session = HibernateUtil.getSession();
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
        Session session = HibernateUtil.getSession();
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
        Session session = HibernateUtil.getSession();
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
        Session session = HibernateUtil.getSession();
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
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userid);
        user.setPayid(payid);
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void updatePasswordByUserid(String userid, String password) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userid);
        user.setPassword(password);
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateWriteOffByUserId(String userid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userid);
        user.setWriteoff(1);
        session.update(user);
        transaction.commit();
        session.close();
    }
}
