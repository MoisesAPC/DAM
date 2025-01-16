package ut4_actividad3;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Consultas3 {

	public static void main(String[] args) {

		SessionFactory sesion = consultaBD.getCurrentSessionFactoryConfig();
		Session s1 = sesion.openSession();

		Dept dep = new Dept();

		dep = (Dept) s1.get(Dept.class, 10);
		System.out.println("Nombre: " + dep.getDname());

		Set <Emp> listaemp = dep.getEmps();
		
		Emp emple = new Emp();
		for (Emp e : listaemp) {
			System.out.print("Nombre: " + e.getEname()+ " - ");
			System.out.println("Oficio: " + e.getJob());
		}

		s1.close();

	}
}
