package ut4_actividad3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import prog.HibernateUtil;
import clases.Empleados;
import clases.Departamentos;

public class Consultas4 {

	public static void main(String[] args) {

		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session s1 = sesion.openSession();

	
		//-------------------------------------------------------------------------------
		
		//Consulta sobre dos tablas
		Query q=s1.createQuery("from Empleados e, Departamentos d where e.departamentos.deptno=d.deptno and localidad = 'Arucas' ");
		
				
		//---------------------------------------------------------------------------------------
		
				
		Iterator i = q.iterate();
		while (i.hasNext()) {
			Object[] reg = (Object[]) i.next();
			Empleados emp=(Empleados) reg[0];
			Departamentos dep=(Departamentos) reg[1];
			System.out.println("Nombre: " + emp.getNombre());
			System.out.println("Oficio: " + emp.getCargo());
			System.out.println("Localidad: " + dep.getLocalidad());
		}
		
		s1.close();

	}

}