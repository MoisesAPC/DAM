package prog;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import prog.HibernateUtil;
import clases.Empleados;
import clases.Departamentos;

public class Consultagrupo {

	public static void main(String[] args) {

		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session s1 = sesion.openSession();
		
		//C�lculo del salario medio de los empleados.
		//Si se devuelven varias filas, se utilizan los objetos.
		Query q=s1.createQuery("select avg(salario) from Empleados");
		Double media=(Double) q.uniqueResult();
		System.out.println("Salario medio: " + media);
		
		s1.close();

	}

}