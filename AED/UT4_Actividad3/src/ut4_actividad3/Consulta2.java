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

public class Consulta2 {

	public static void main(String[] args) {

		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session s1 = sesion.openSession();

		Departamentos dep = new Departamentos();
		
		
		//createQuery realiza consulta en HQL
		//list() recupera datos de la consulta
		
		/*Query q=s1.createQuery("from Departamentos");
		List <Departamentos> lista=q.list();
		
		Iterator <Departamentos> itdep = lista.iterator();
		while (itdep.hasNext()) {
			dep= (Departamentos) itdep.next();
			System.out.println("Departamento: " + dep.getNombre());
		}*/
		
		//Query q=s1.createQuery("from Empleados e, Departamentos d where e.departamentos.deptno=d.deptno and d.deptno=10 and e.cargo = 'Operador' ");
		
		//Consulta construida
		//Query q=s1.createQuery("from Empleados where deptno=10 and cargo = 'Operador' ");
		
		//Consulta con par�metros por nombre
		/*Query q=s1.createQuery("from Empleados where deptno=:num and cargo = :ofi ");
		q.setInteger("num", 10);
		q.setString("ofi", "Operador");*/
		
		//Consulta con par�metros por posici�n
		/*Query q=s1.createQuery("from Empleados where deptno=? and cargo = ? ");
		q.setInteger(0, 10);
		q.setString(1, "Operador");*/
		
		//Consulta con par�metros en una lista
		List  numeros=new ArrayList ();
		numeros.add((byte) 10);
		numeros.add((byte) 20);
		
		Query q=s1.createQuery("from Empleados where deptno in (:paramlista)");
		q.setParameterList("paramlista", numeros);
			
		List <Empleados> listaemp = q.list();
		Iterator <Empleados> itemp = listaemp.iterator();
		Empleados emp=new Empleados(); 
		while (itemp.hasNext()) {
			emp= (Empleados) itemp.next();
			System.out.println("Nombre: " + emp.getNombre());
			System.out.println("Oficio: " + emp.getCargo());
		}
		/*dep = (Departamentos) s1.get(Departamentos.class, 10);
		System.out.println("Nombre: " + dep.getNombre());

		Set <Empleados> listaemp = dep.getEmpleadoses();
		Iterator <Empleados> itemp = listaemp.iterator();
		while (itemp.hasNext()) {
			Empleados emple = new Empleados();
			emple = itemp.next();
			System.out.println("Nombre empleado: " + emple.getNombre()
					+ "  Salario: " + emple.getSalario());

		}
		*/
	
		
		
				s1.close();

	}

}