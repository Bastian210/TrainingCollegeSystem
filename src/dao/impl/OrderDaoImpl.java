package dao.impl;

import dao.OrderDao;
import model.Ordermessage;
import model.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    
    private final SessionFactory sessionFactory;

    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Orders orders) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(orders);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Orders orders) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(orders);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(String orderid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Orders orders = session.get(Orders.class,orderid);
        session.delete(orders);
        transaction.commit();
        session.close();
    }

    @Override
    public Orders findOrderByOrderId(String orderid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Orders orders = session.get(Orders.class,orderid);
        transaction.commit();
        session.close();
        return orders;
    }

    @Override
    public String getMaxId() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from model.Orders";
        Query query = session.createQuery(hql);
        List list = query.list();
        if(list.size()==0){
            return "10000000";
        }
        Orders orders = (Orders) list.get(list.size()-1);
        transaction.commit();
        session.close();
        return orders.getOrderid();
    }

    @Override
    public List findOrderListByUserId(String userid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Orders as o where o.userid = '%s' order by o.ordertime desc", userid);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void saveOrderMessage(Ordermessage ordermessage) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(ordermessage);
        transaction.commit();
        session.close();
    }

    @Override
    public List getOrderMessageListByOrderId(String orderid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Ordermessage as om where om.orderid = '%s'", orderid);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void deleteOrderMessageByOrderId(String orderid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Ordermessage as om where om.orderid = '%s'", orderid);
        Query query = session.createQuery(hql);
        List list = query.list();
        for(int i=0;i<list.size();i++){
            Ordermessage orders = (Ordermessage) list.get(i);
            session.delete(orders);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List findOrderListByState(String state) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Orders as o where o.state = '%s' order by o.ordertime desc", state);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List findOrderListByInstitutionId(String institutionid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Orders as o where o.institutionid = '%s' order by o.ordertime desc", institutionid);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }
}
