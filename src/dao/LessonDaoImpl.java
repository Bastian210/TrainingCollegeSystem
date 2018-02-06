package dao;

import model.Lesson;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

@Repository
public class LessonDaoImpl implements LessonDao {
    @Override
    public void save(Lesson lesson) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(lesson);
        transaction.commit();
        session.close();
    }
}
