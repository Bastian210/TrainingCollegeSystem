package dao.impl;

import dao.BillDao;
import model.Bill;
import model.key.BillKey;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.util.List;

@Repository
public class BillDaoImpl implements BillDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public BillDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Bill bill) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(bill);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Bill bill) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(bill);
        transaction.commit();
        session.close();
    }

    @Override
    public Bill getBillByBillKey(BillKey billKey) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Bill bill = session.get(Bill.class,billKey);
        transaction.commit();
        session.close();
        return bill;
    }

    @Override
    public List getBillListById(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Bill as b where b.id='%s'", id);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }
}
