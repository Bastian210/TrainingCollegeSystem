package dao;

import model.Ordermessage;
import model.Orders;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Override
    public void save(Orders orders) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(orders);
        transaction.commit();
        session.close();
    }

    @Override
    public String getMaxId() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from model.Orders";
        Query query = session.createQuery(hql);
        List list = query.list();
        if(list.size()==0){
            return "10000001";
        }
        Orders orders = (Orders) list.get(list.size()-1);
        transaction.commit();
        session.close();
        return orders.getOrderid();
    }

    @Override
    public void saveOrderMessage(Ordermessage ordermessage) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(ordermessage);
        transaction.commit();
        session.close();
    }
}
