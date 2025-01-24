package ut4_actividadevaluacion;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {
	private static void imprimirTablasOficinas(Session sesion) {
		Query query = sesion.createQuery("FROM Oficinas");
		
	    List<Oficinas> oficinas = query.list();
	    
	    for (Oficinas oficina : oficinas) {
	        System.out.println(" - Oficina - ");
	        System.out.println("codOfi: " + oficina.getCodOfi());
	        System.out.println("nombre: " + oficina.getNombre());
	        System.out.println("direccion: " + oficina.getDireccion());
	        System.out.println("localidad: " + oficina.getLocalidad());
	        System.out.println("--------------------");
	    }
	}
	
	private static void imprimirTablasEmpleados(Session sesion) {
		Query query = sesion.createQuery("FROM Empleados");
		
	    List<Empleados> empleados = query.list();
	    
	    for (Empleados empleado : empleados) {
	        System.out.println(" - Empleado - ");
	        System.out.println("nif: " + empleado.getNif());
	        System.out.println("oficinas: " + empleado.getOficinas());
	        System.out.println("nombre: " + empleado.getNombre());
	        System.out.println("direccion: " + empleado.getDireccion());
	        System.out.println("cargo: " + empleado.getCargo());
	        System.out.println("--------------------");
	    }
	}
	
	/**
	 * Ejercicio 3
	 * 
	 * @note Para solucionar errores de "org.hibernate.NonUniqueObjectException",
	 * usamos "merge()" en vez de "save()"
	 */
	private static void insertarEntradasDeCadaTabla(Session sesion) {
		// Insertamos entradas en la tabla "Oficinas"
		Transaction transaction = sesion.beginTransaction();
		
		Oficinas oficina1 = new Oficinas("20", "Orange", "Av. Canarias", "Santa Lucia");
        sesion.merge(oficina1);

        Oficinas oficina2 = new Oficinas("50", "VodafoneSUR", "Alcorac 50", "Aguimes");
        sesion.merge(oficina2);

        Oficinas oficina3 = new Oficinas("10", "Movistar", "La Mareta", "Telde");
        sesion.merge(oficina3);
        
        Empleados empleado1 = new Empleados("12345678A", oficina1, "Juan Pérez", "Calle Luna 10", "tecnico");
        sesion.merge(empleado1);

        Empleados empleado2 = new Empleados("23456789B", oficina1, "María García", "Avenida Sol 20", "tecnico");
        sesion.merge(empleado2);

        Empleados empleado3 = new Empleados("34567890C", oficina2, "Luis Rodríguez", "Plaza Mayor 30", "director");
        sesion.merge(empleado3);
        
        Empleados empleado4 = new Empleados("44567890C", oficina3, "Pedro Rodríguez", "Plaza Mayor 30", "comercial");
        sesion.merge(empleado4);
        
        Empleados empleado5 = new Empleados("54567890C", oficina3, "Juan Rodríguez", "Plaza Mayor 30", "director");
        sesion.merge(empleado5);
        
        transaction.commit();
	}
	
	/**
	 * Ejercicio 4
	 */
	public static void ejercicio4(Session sesion) {
		// Apartado a)
		// Obtener todos los datos de los empleados.
		System.out.println("- Apartado a) -");
		imprimirTablasEmpleados(sesion);
		
		// Apartado b)
		// Modificar el cargo de todos los empleados de la oficina 50, de forma que sean todos comerciales
		System.out.println("- Apartado b) -");
		
		System.out.println("- ANTES -");
		imprimirTablasEmpleados(sesion);
		
		ejercicio4b(sesion);
		
		System.out.println("- DESPUES -");
		imprimirTablasEmpleados(sesion);
		
		// Apartado c)
		// Mostrar cuántos empleados hay por cada oficina (con función de agrupamiento).
		// Las funciones de agrupamiento son: avg(), sum(), min(), max(), count(), count(distinct), count(all)
		System.out.println("- Apartado c) -");

		ejercicio4c(sesion);
		
		// Apartado d)
		// Obtener el nombre de las oficinas de Telde, donde la localidad serpasado por
		// parámetro (con el método que tú elijas).
		System.out.println("- Apartado d) -");

		ejercicio4d(sesion);
		
		// Apartado e)
		// Elimina las oficinas de Telde
		System.out.println("- ANTES -");
		imprimirTablasEmpleados(sesion);
		
		ejercicio4e(sesion);
		
		System.out.println("- DESPUES -");
		imprimirTablasEmpleados(sesion);
	}
	
	/**
	 * Ejercicio 4 (apartado b)
	 */
	public static void ejercicio4b(Session sesion) {
		Transaction transaction = sesion.beginTransaction();
		
		Query query = sesion.createQuery("UPDATE Empleados e SET e.cargo = 'Comercial' WHERE e.oficinas.codOfi = :codOfi");
		query.setString("codOfi", "50");
		int filasModificadas = query.executeUpdate();
		System.out.println("Se han modificado " + filasModificadas + " filas");
		
		transaction.commit();
	}
	
	/**
	 * Ejercicio 4 (apartado c)
	 * 
	 * @note Las funciones de agrupamiento son:
	 * avg(), sum(), min(), max(), count(), count(distinct), count(all)
	 * 
	 * Nosotros usaremos count()
	 */
	public static void ejercicio4c(Session sesion) {
		// En el SELECT
		// "e.oficinas.nombre" se corresponde con "result[0]"
		// "count(e)" se corresponde con "result[1]"
	    Query query = sesion.createQuery("SELECT e.oficinas.nombre, count(e) FROM Empleados e GROUP BY e.oficinas.nombre");
	    List<Object[]> results = query.list();

	    for (Object[] result : results) {
	        String nombreOficina = (String) result[0];
	        Long cantidadEmpleados = (Long) result[1];
	        System.out.println(nombreOficina + ": " + cantidadEmpleados);
	    }
	}
	
	/**
	 * Ejercicio 4 (apartado d)
	 */
	public static void ejercicio4d(Session sesion) {
		Scanner teclado = new Scanner(System.in);
		
		System.out.print("Escriba la localidad: ");
		String localidadEscrita = teclado.nextLine();
		
		Query query = sesion.createQuery("SELECT o.nombre FROM Oficinas o WHERE o.localidad = :localidad");
		query.setParameter("localidad", localidadEscrita);
		List<String> listaOficinas = query.list();
		
		if (listaOficinas.isEmpty()) {
			System.out.println("No hay oficinas en " + localidadEscrita);
		}
		else {
			System.out.println("Oficinas de " + localidadEscrita + ": ");
			 for (String nombreOficina : listaOficinas) {
				 System.out.println("- " + nombreOficina);
		     }
		}
	}
	
	/**
	 * Ejercicio 4 (apartado e)
	 */
	public static void ejercicio4e(Session sesion) {
		final String oficinaABorrar = "Telde";
		Transaction transaction = sesion.beginTransaction();
		
		// Primero, borramos todas las referencias a la oficina de "Telde" de la tabla de Empleados
        Query query1 = sesion.createQuery("UPDATE Empleados e SET e.oficinas = NULL WHERE e.oficinas.localidad = :localidad");
        query1.setParameter("localidad", oficinaABorrar);
        query1.executeUpdate();
		
		// Ahora sí borramos las oficinas de "Telde"
		Query query2 = sesion.createQuery("DELETE FROM Oficinas o WHERE o.localidad = :localidad");
		query2.setParameter("localidad", oficinaABorrar);
		query2.executeUpdate();
				
		// Finalmente hacemos un commit para aplicar los cambios
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
		imprimirTablasOficinas(sesion);
		imprimirTablasEmpleados(sesion);
		
		// Hacemos la inserción
		insertarEntradasDeCadaTabla(sesion);
		
		System.out.println("- DESPUES -");
		imprimirTablasOficinas(sesion);
		imprimirTablasEmpleados(sesion);
		
		/**
		 * Ejercicio 4
		 */
		System.out.println("--- EJERCICIO 4 ---");
		ejercicio4(sesion);
		
		sesion.close();
	}
}
