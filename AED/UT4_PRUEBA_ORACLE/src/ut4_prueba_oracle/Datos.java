package ORACLE;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Datos {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = getCurrentSessionFromConfig(); Session session = sessionFactory.openSession()) {
            insertarDatos(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    private static SessionFactory getCurrentSessionFromConfig() {
        Configuration config = new Configuration();
        
    
        System.out.println("Directorio de trabajo: " + System.getProperty("user.dir"));

      
        config.configure("ORACLE/hibernate.cfg.xml");  
        return config.buildSessionFactory();
    }

   
    private static void insertarDatos(Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            
            Oficinas of1 = new Oficinas("20", "Orange", "Av. Canarias", "Santa Lucía", null);
            Oficinas of2 = new Oficinas("50", "VodafoneSUR", "Alcorac 50", "Agüimes", null);
            Oficinas of3 = new Oficinas("10", "Movistar", "La Mareta", "Telde", null);

            Empleados emple1 = new Empleados ("001", of1, "Judit Mendoza", "Av Alcalde", "Picking");
            Empleados emple2= new Empleados("002", of2, "Daniel Hernandez", "Campo Amor", "Comercial");
            Empleados emple3 = new Empleados("003", of3, "Pepelu", "Calle Sevilla", "RRhh");

           
            session.save(of1);
            session.save(of2);
            session.save(of3);
            session.save(emple1);
            session.save(emple2);
            session.save(emple3);
            
            transaction.commit();
            System.out.println("Datos insertados correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
