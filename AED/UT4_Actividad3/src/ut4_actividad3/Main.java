package ut4_actividad3;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {
	private static void imprimirTablasEmpleados(Query query) {
	    List<Empleados> empleados = query.list();
	    
	    for (Empleados empleado : empleados) {
	        System.out.println(" - Empleado - ");
	        System.out.println("Cargo: " + empleado.getId().getCargo());
	        System.out.println("Oficina: " + empleado.getId().getOfi());
	        System.out.println("Salario: " + empleado.getId().getSalario());
	        System.out.println("Departamento: " + empleado.getId().getDeptno());
	        System.out.println("--------------------");
	    }
	}

	private static void imprimirTablasEmpleadosYDepartamentos(Query query) {
	    List<Object[]> results = query.list();
	    
	    for (Object[] registro : results) {
	        Empleados empleado = (Empleados) registro[0];
	        Departamentos departamento = (Departamentos) registro[1];
	        
	        // Empleado
	        System.out.println(" - Empleado - ");
	        System.out.println("Cargo: " + empleado.getId().getCargo());
	        System.out.println("Oficina: " + empleado.getId().getOfi());
	        System.out.println("Salario: " + empleado.getId().getSalario());
	        System.out.println("Departamento: " + empleado.getId().getDeptno());
	        
	        // Departamento
	        System.out.println(" - Departamento - ");
	        System.out.println("deptno: " + departamento.getDeptno());
	        System.out.println("nombre: " + departamento.getNombre());
	        System.out.println("localidad: " + departamento.getLocalidad());
	        
	        System.out.println("--------------------");
	    }
	}

	
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		
		// Abrimos la sesión
		SessionFactory sesionFactory = HibernateUtil.getSessionFactory();
		Session sesion = sesionFactory.openSession();
		
		/**
		 * Ejercicio 1.
		 * 
		 * Hacer una consulta de todos los empleados que trabajan en dos departamentos (con
		 * parámetros solicitados al usuario y que se pasan a la query) y que cobran ms de 1500€
		 */
		System.out.println("--- EJERCICIO 1 ---");
		
		System.out.print("Introduce el deptno del departamento 1: ");
		int departamento1 = teclado.nextInt();
		
		System.out.print("Introduce el deptno del departamento 2: ");
		int departamento2 = teclado.nextInt();
		
		// Hacemos la consulta en HQL
		// Lo de "e.departamentos.deptno" es porque así se generó la clase en el Java
		// Es decir, dentro de "Empleados" se guarda una instancia de "Departamentos"
		Query queryEjercicio1_1 = sesion.createQuery("FROM Empleados e WHERE e.departamentos.deptno = :deptno AND e.id.salario > 1500");
		queryEjercicio1_1.setInteger("deptno", departamento1);
		
		Query queryEjercicio1_2 = sesion.createQuery("FROM Empleados e WHERE e.departamentos.deptno = :deptno AND e.id.salario > 1500");
		queryEjercicio1_2.setInteger("deptno", departamento2);
		
		// Imprimimos el contenido de las tablas que obtuvimos en el query
		imprimirTablasEmpleados(queryEjercicio1_1);
		imprimirTablasEmpleados(queryEjercicio1_2);
		
		/**
		 * Ejercicio 2.
		 * 
		 *  Modificar el sueldo de los empleados que pertenecen a los departamentos anteriores e
		 *  incrementarles el sueldo actual en 500€
		 */
		System.out.println("--- EJERCICIO 2 ---");
		
		// Empezamos la transaction (necesario al hacer UPDATES y otras modificaciones a las tablas)
		Transaction transaction = sesion.beginTransaction();
		
		Query queryEjercicio2_1 = sesion.createQuery("UPDATE Empleados e SET e.id.salario = e.id.salario + 500 WHERE e.departamentos.deptno = :deptno");
		queryEjercicio2_1.setInteger("deptno", departamento1);
		int filasModificadas = queryEjercicio2_1.executeUpdate();
		System.out.println("Se han modificado " + filasModificadas + " filas");
		
		Query queryEjercicio2_2 = sesion.createQuery("UPDATE Empleados e SET e.id.salario = e.id.salario + 500 WHERE e.departamentos.deptno = :deptno");
		queryEjercicio2_2.setInteger("deptno", departamento2);
		filasModificadas = queryEjercicio2_2.executeUpdate();
		System.out.println("Se han modificado " + filasModificadas + " filas");
		
		imprimirTablasEmpleados(queryEjercicio1_1);
		imprimirTablasEmpleados(queryEjercicio1_2);
		
		// Finalmente hacemos un commit para aplicar los cambios
		transaction.commit();
		
		/**
		 * Ejercicio 3.
		 * 
		 *  Eliminar los empleados que trabajan en la localidad de "Agüimes"
		 */
		System.out.println("--- EJERCICIO 3 ---");
		
		// Empezamos la transaction (necesario al hacer UPDATES y otras modificaciones a las tablas)
		transaction = sesion.beginTransaction();
				
		// Primero, seleccionamos los "EmpleadoId" de los empleados a borrar
		Query selectQuery = sesion.createQuery("SELECT e.id FROM Empleados e WHERE e.departamentos.localidad = :localidad");
		selectQuery.setParameter("localidad", "Agüimes");
		List<EmpleadosId> empleadosABorrar = selectQuery.list();

		// Ahora sí borramos los empleados de la lista que obtuvimos anteriormente
		Query deleteQuery = sesion.createQuery("DELETE FROM Empleados e WHERE e.id IN (:ids)");
		deleteQuery.setParameterList("ids", empleadosABorrar);
		
		// Hacemos la eliminación
		deleteQuery.executeUpdate();
		
		// Finalmente hacemos un commit para aplicar los cambios
		transaction.commit();
		
		/**
		 * Ejercicio 4.
		 * 
		 *  Preparar una consulta que te permita visualizar el contenido de las dos tablas
		 *  relacionadas, con todos sus campos.
		 */
		System.out.println("--- EJERCICIO 4 ---");
		
		// Consulta sobre dos tablas (en este caso, Empleados y Departamentos)
		Query queryEjercicio4 = sesion.createQuery("from Empleados e, Departamentos d where e.departamentos.deptno=d.deptno");
		
		imprimirTablasEmpleadosYDepartamentos(queryEjercicio4);
		
		sesion.close();
	}
}
