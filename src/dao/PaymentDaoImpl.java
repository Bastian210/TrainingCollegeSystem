package dao;

import model.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

@Repository
public class PaymentDaoImpl implements PaymentDao {
    @Override
    public void update(Payment payment) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(payment);
        transaction.commit();
        session.close();
    }

    @Override
    public Payment findPaymentByPayId(String payid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Payment payment = session.get(Payment.class,payid);
        transaction.commit();
        session.close();
        return payment;
    }

    @Override
    public void updatePasswordByPayid(String payid, String password) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Payment payment = session.get(Payment.class,payid);
        payment.setPassword(password);
        session.update(payment);
        transaction.commit();
        session.close();
    }

    @Override
    public Payment getManagePayment() {
        return findPaymentByPayId("30001");
    }
}
