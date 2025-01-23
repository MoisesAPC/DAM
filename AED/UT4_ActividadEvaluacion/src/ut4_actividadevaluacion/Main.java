package ut4_actividadevaluacion;

import java.util.Iterator;
import java.util.List;
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
	 */
	private static void insertarEntradasDeCadaTabla(Session sesion) {
		// Insertamos entradas en la tabla "Oficinas"
		Transaction transaction = sesion.beginTransaction();
		
		sesion.createQuery(
				"INSERT INTO Oficinas (codOfi, nombre, direccion, localidad) " +
                "SELECT '20', 'Orange', 'Av. Canarias', 'Santa Lucia'"
						).executeUpdate();
		sesion.createQuery(
				"INSERT INTO Oficinas (codOfi, nombre, direccion, localidad) " +
                "SELECT '50', 'VodafoneSUR', 'Alcorac 50', 'Aguimes'"
						).executeUpdate();
		sesion.createQuery(
				"INSERT INTO Oficinas (codOfi, nombre, direccion, localidad) " +
                "SELECT '10', 'Movistar', 'La Mareta', 'Telde'"
						).executeUpdate();
		
		// Insertamos entradas en la tabla "Empleados"
		// La última sentencia (SELECT o FROM Oficinas o es necesaria para asociar el codOfi de Empleados
		// con el respectivo objeto en la tabla de oficinas)
		sesion.createQuery(
				"INSERT INTO Empleados (nif, nombre, direccion, cargo, oficinas) " +
                "SELECT '12345678A', 'Juan Pérez', 'Calle Luna 10', 'director', " +
                "(SELECT o FROM Oficinas o WHERE o.codOfi = '20')"
						).executeUpdate();
		sesion.createQuery(
				"INSERT INTO Empleados (nif, nombre, direccion, cargo, oficinas) " +
                "SELECT '23456789B', 'María García', 'Avenida Sol 20', 'comercial', " +
                "(SELECT o FROM Oficinas o WHERE o.codOfi = '50')"
						).executeUpdate();
		sesion.createQuery(
				"INSERT INTO Empleados (nif, nombre, direccion, cargo, oficinas) " +
                "SELECT '34567890C', 'Pedro Rodríguez', 'Plaza Mayor 30', 'tecnico', " +
                "(SELECT o FROM Oficinas o WHERE o.codOfi = '10')"
						).executeUpdate();
		
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
		
		sesion.close();
	}
}
