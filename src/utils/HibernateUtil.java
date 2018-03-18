package utils;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	 public static SessionFactory getSessionFactory(){
		 try {
				Configuration config;
				ServiceRegistry serviceRegistry;
				config = new Configuration().configure();
				config.addAnnotatedClass(Bill.class);
				config.addAnnotatedClass(Institution.class);
				config.addAnnotatedClass(Lesson.class);
				config.addAnnotatedClass(Ordermessage.class);
				config.addAnnotatedClass(Orders.class);
				config.addAnnotatedClass(Payment.class);
				config.addAnnotatedClass(Plans.class);
				config.addAnnotatedClass(SecurityCode.class);
				config.addAnnotatedClass(Teachers.class);
				config.addAnnotatedClass(User.class);
				serviceRegistry =new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
				sessionFactory=config.buildSessionFactory(serviceRegistry);	
				return sessionFactory;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	 }

	 public static Session getSession(){
	 	return getSessionFactory().getCurrentSession();
	 }
}
