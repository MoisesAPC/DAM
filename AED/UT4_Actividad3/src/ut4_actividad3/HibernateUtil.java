package ut4_actividad3;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Esta es la clase HibernateUtil que se incluye en NetBeans, no en Eclipse... pero que te puedes crear como tu quieras.

public class HibernateUtil {

     private static final SessionFactory sessionFactory;
     static {
         try {
             // Create the SessionFactory from standard (hibernate.cfg.xml)
        	 // config file.
            sessionFactory = new Configuration().configure().buildSessionFactory();
           } catch (Throwable ex) {
             // Log the exception.
             System.err.println("Initial SessionFactory creation failed." + ex);
             throw new ExceptionInInitializerError(ex);
         }
     }
     
     public static SessionFactory getSessionFactory() {
         return sessionFactory;
     }
 }