package org.carcv.persistence;

import javax.persistence.OneToMany;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	private static SessionFactory configureSessionFactory() {
		System.err.println(OneToMany.class.getProtectionDomain().getCodeSource().getLocation());
		System.err.println(org.hibernate.mapping.OneToMany.class.getProtectionDomain().getCodeSource().getLocation());
		
		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
				configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;

	}

	private static SessionFactory configureSessionFactory(
			String configureFilename) {
		Configuration configuration = new Configuration();
		configuration.configure(configureFilename);
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
				configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		return configureSessionFactory();
	}

	public static SessionFactory getSessionFactory(String configureFilename) {
		return configureSessionFactory(configureFilename);
	}

}