package org.carcv.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
 
public class HibernateUtil {
 
    private static final SessionFactory sessionFactory = buildSessionFactory();
 
    private static SessionFactory buildSessionFactory() {
    	
    	String hibernateCfgXmlFile = GetClassLoader.fromContext().getClass().getResource("/hibernate.cfg.xml").getPath();
    	System.out.println(hibernateCfgXmlFile);
    	
    	return HibernateUtil.buildSessionFactory(hibernateCfgXmlFile);
    }
    
    public static SessionFactory buildSessionFactory(String configureFilename) {
        try {
            // Create the SessionFactory from annotations
            return new AnnotationConfiguration().configure(configureFilename).buildSessionFactory();
 
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public static void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }
 
}