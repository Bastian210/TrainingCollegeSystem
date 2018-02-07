package dao;

import model.Lesson;
import model.LessonKey;
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

    @Override
    public Lesson findLessonByLessonKey(LessonKey lessonKey) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Lesson lesson = session.get(Lesson.class,lessonKey);
        transaction.commit();
        session.close();
        return lesson;
    }
}
