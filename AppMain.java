package com.jcg.hibernate.maven;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class AppMain {

	static Session session;
	static SessionFactory sessionFactoryObj;
	

	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");
		configObj.addAnnotatedClass(com.jcg.hibernate.maven.User.class);

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		return sessionFactoryObj;
	}

	 //@SuppressWarnings("deprecation") //= if any deprecated methods 
	public static void main(String[] args) {
		System.out.println(".......Hibernate Maven Example.......\n");
		try {
			session = buildSessionFactory().openSession();
			session.beginTransaction();

			for(int i = 31; i <= 48; i++) {
				User user = new User();
				user.setUserid(i);
				user.setUsername("Editor: "+i);
				user.setCreatedBy("Administrator");
				user.setCreatedDate(new Date());
				
				session.save(user); // here session is old method no worry run it , it can execute
				//session.merge(user);
			}
				
					
				
			System.out.println("\n.......Records Saved Successfully To The Database.......\n");

			// Committing The Transactions To The Database
			session.getTransaction().commit();
		} catch(Exception sqlException) {
			if(null != session.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
}
