package ut4_examen;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {
	private static void imprimirTablasAlumnos(Session sesion) {
		Query query = sesion.createQuery("FROM Alumnos");
		
	    List<Alumnos> alumnos = query.list();
	    
	    for (Alumnos alumno : alumnos) {
	        System.out.println(" - Empleado - ");
	        System.out.println("nif: " + alumno.getNif());
	        System.out.println("nombre: " + alumno.getNombre());
	        System.out.println("cial: " + alumno.getCial());
	        System.out.println("telefono: " + alumno.getTelefono());
	        System.out.println("--------------------");
	    }
	}
	
	/**
	 * Ejercicio 3
	 * 
	 * @note Para solucionar errores de "org.hibernate.NonUniqueObjectException",
	 * usamos "merge()" en vez de "save()"
	 */
	private static void insertarEntradasAlumnos(Session sesion) {
		Transaction transaction = sesion.beginTransaction();
		
		Alumnos alumno1 = new Alumnos("12345678X", "Pepe", "1234567891", "928656565");
        sesion.merge(alumno1);

        Alumnos alumno2 = new Alumnos("98765432V", "Manolo", "6398527410", "928527474");
        sesion.merge(alumno2);

        Alumnos alumno3 = new Alumnos("35768965N", "Luis", "0212587416", "966548787");
        sesion.merge(alumno3);
        
        transaction.commit();
	}
	
	/**
	 * Ejercicio 5
	 */
	public static void ejercicio5(Session sesion) {
		// Apartado a)
		System.out.println("\n- Apartado a) -");
		imprimirTablasAlumnos(sesion);
		
		// Apartado b)
		System.out.println("\n- Apartado b) -");
		ejercicio5b(sesion);
		
		// Apartado c)
		System.out.println("\n- Apartado c) -");

		System.out.println("- ANTES -");
		imprimirTablasAlumnos(sesion);
		
		ejercicio5c(sesion);
		
		// Apartado d)
		System.out.println("\n- Apartado d) -");
		System.out.println("- ANTES -");
		imprimirTablasAlumnos(sesion);
		
		ejercicio5d(sesion);
		
		System.out.println("- DESPUES -");
		imprimirTablasAlumnos(sesion);
	}
	
	/**
	 * Ejercicio 5 (apartado b)
	 */
	public static void ejercicio5b(Session sesion) {
		Scanner teclado = new Scanner(System.in);
		
		System.out.print("Escriba el cial: ");
		String alumnoCial = teclado.nextLine();
		
		Query query = sesion.createQuery("SELECT a.nombre FROM Alumnos a WHERE a.cial = :cial");
		query.setParameter("cial", alumnoCial);
		List<String> resultados = query.list();
		
		if (resultados.isEmpty()) {
			System.out.println("No hay alumno con ese CIAL");
		}
		else {
			 for (String nombreAlumno : resultados) {
				 System.out.println("- Alumno: " + nombreAlumno + " (CIAL: " + alumnoCial + ")");
		     }
		}
	}
	
	/**
	 * Ejercicio 5 (apartado c)
	 */
	public static void ejercicio5c(Session sesion) {
		Transaction transaction = sesion.beginTransaction();
		
	    Query query = sesion.createQuery("UPDATE Alumnos a SET a.telefono = :telefonoNuevo WHERE a.telefono LIKE '928%'");
	    query.setParameter("telefonoNuevo", "922123123");
	    query.executeUpdate();
	    
	    transaction.commit();
	}
	
	/**
	 * Ejercicio 5 (apartado d)
	 */
	public static void ejercicio5d(Session sesion) {
		Scanner teclado = new Scanner(System.in);
		
		System.out.print("Escriba el NIF: ");
		String alumnoNif = teclado.nextLine();
		
		Transaction transaction = sesion.beginTransaction();

		Query query = sesion.createQuery("DELETE FROM Alumnos a WHERE a.nif = :alumnoNif");
		query.setParameter("alumnoNif", alumnoNif);
		query.executeUpdate();

		transaction.commit();
	}
	
	public static void main(String[] args) {
		// Abrimos la sesión
		SessionFactory sesionFactory = HibernateUtil.getSessionFactory();
		Session sesion = sesionFactory.openSession();
				
		/**
		 * Ejercicio 3
		 */
		System.out.println("--- EJERCICIO 3 ---");
		
		System.out.println("- ANTES -");
		imprimirTablasAlumnos(sesion);
		
		// Hacemos la inserción
		insertarEntradasAlumnos(sesion);
		
		System.out.println("- DESPUES -");
		imprimirTablasAlumnos(sesion);
		
		/**
		 * Ejercicio 5
		 */
		System.out.println("--- EJERCICIO 5 ---");
		ejercicio5(sesion);
		
		sesion.close();
	}
}
