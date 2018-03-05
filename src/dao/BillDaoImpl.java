package dao;

import model.Bill;
import model.BillKey;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class BillDaoImpl implements BillDao {

    @Override
    public void save(Bill bill) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(bill);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Bill bill) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(bill);
        transaction.commit();
        session.close();
    }

    @Override
    public Bill getBillByBillKey(BillKey billKey) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Bill bill = session.get(Bill.class,billKey);
        transaction.commit();
        session.close();
        return bill;
    }
}
