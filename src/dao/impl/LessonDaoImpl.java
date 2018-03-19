package dao.impl;

import dao.LessonDao;
import model.Lesson;
import model.key.LessonKey;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.util.Collections;
import java.util.List;

@Repository
public class LessonDaoImpl implements LessonDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public LessonDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Lesson lesson) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(lesson);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Lesson lesson) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(lesson);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Lesson lesson) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(lesson);
        transaction.commit();
        session.close();
    }

    @Override
    public Lesson findLessonByLessonKey(LessonKey lessonKey) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Lesson lesson = session.get(Lesson.class,lessonKey);
        transaction.commit();
        session.close();
        return lesson;
    }

    @Override
    public Lesson findLessonByLessonidAndName(String lessonid, String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Lesson as l where l.lessonid = '%s' and l.name = '%s'", lessonid, name);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return (Lesson) list.get(0);
    }

    @Override
    public List findLessonListByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Lesson as l where l.name = '%s'", name);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        Collections.reverse(list);
        return list;
    }

    @Override
    public void updateStateByLessonid(String lessonid, String state){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Lesson as l where l.lessonid = '%s'", lessonid);
        Query query = session.createQuery(hql);
        List list = query.list();
        for(int i=0;i<list.size();i++){
            Lesson lesson = (Lesson) list.get(i);
            lesson.setState(state);
            session.update(lesson);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List findLessonByLessonidAndClassid(String lessonid, String classtype, String classid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("from model.Lesson as l where l.lessonid='%s' and l.classtype = '%s' and l.classid = '%s'", lessonid, classtype, classid);
        Query query = session.createQuery(hql);
        List list = query.list();
        transaction.commit();
        session.close();
        return list;
    }
}
