package prog;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConexPostgre {
	public static void main(String[] args) {
		SessionFactory session = getCurrentSessionFromConfig();
		Session s1 = session.openSession();
		
		Departamentos d1 = new Departamentos();
		
		d1 = (Departamentos) s1.get(Departamentos.class, 55);
		System.out.println("Nombre: " + d1.getNombre());
		s1.close();
	}
	
	public static SessionFactory getCurrentSessionFromConfig() {
		// Es un apaño de la clase HibernateUtil que incorpora NetBeans
		
		// SessionFactory in Hibernate 5 example
		Configuration config = new Configuration();
		config.configure();
		
		// local SessionFactory has been created
		SessionFactory sessionFactory = config.buildSessionFactory();
		// Session session = sessionFactory.getCurrentSession();
		return sessionFactory;
	}
}
