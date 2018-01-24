package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public void save(User user) {
        Session session = HibernateUtil.getSession();
        session.save(user);
    }

    @Override
    public void delete(String userid) {

    }

    @Override
    public User findUserByUserid(String userid) {
        return null;
    }

    public String findPasswordByEmail(String email){
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
            return ((User)list.get(0)).getPassword();
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
        return user.getUserid();
    }
}
