package ORACLE;



import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ConexAED {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = getSessionFactory(); Session session = sessionFactory.openSession()) {
            
            obtenerDatosEmpleados(session);
            modificarCargoEmpleados(session, "50", "Comercial");
            contarEmpleadosPorOficina(session);
            obtenerOficinasPorLocalidad(session, "Telde");
            eliminarOficinasPorLocalidad(session, "Telde");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory getSessionFactory() {
        Configuration config = new Configuration();

        // Configuración para Oracle
        config.configure("ORACLE/hibernate.cfg.xml");  
        return config.buildSessionFactory();
    }

    private static void obtenerDatosEmpleados(Session session) {
        System.out.println("--- Datos de todos los empleados ---");
        List<Empleados> empleados = session.createQuery("from Empleados", Empleados.class).list();
        empleados.forEach(empleado -> System.out.println("NIF: " + empleado.getNif() + ", Nombre: " + empleado.getNombre() + ", Cargo: " + empleado.getCargo() + ", Dirección: " + empleado.getDirección()));
    }

    private static void modificarCargoEmpleados(Session session, String codOfi, String nuevoCargo) {
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("update Empleados set cargo = :nuevoCargo where oficinas.codOfi = :codOfi");
            query.setParameter("nuevoCargo", nuevoCargo);
            query.setParameter("codOfi", codOfi);
            int filasActualizadas = query.executeUpdate();
            transaction.commit();

            System.out.println("--- Cargo actualizado ---");
            System.out.println(filasActualizadas + " empleados actualizados en la oficina " + codOfi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void contarEmpleadosPorOficina(Session session) {
        System.out.println("--- Número de empleados por oficina ---");
        List<Object[]> resultados = session.createQuery("select oficinas.codOfi, count(*) from Empleados group by oficinas.codOfi", Object[].class).list();
        resultados.forEach(resultado -> System.out.println("Oficina: " + resultado[0] + ", Número de empleados: " + resultado[1]));
    }

    private static void obtenerOficinasPorLocalidad(Session session, String localidad) {
        System.out.println("--- Oficinas en la localidad: " + localidad + " ---");
        List<Oficinas> oficinas = session.createQuery("from Oficinas where localidad = :localidad", Oficinas.class)
                                          .setParameter("localidad", localidad)
                                          .list();
        oficinas.forEach(oficina -> System.out.println("Código: " + oficina.getCodOfi() + ", Nombre: " + oficina.getNombre()));
    }

    private static void eliminarOficinasPorLocalidad(Session session, String localidad) {
        try {
            Transaction transaction = session.beginTransaction();

            // Eliminar empleados vinculados a las oficinas en la localidad
            Query eliminarEmpleadosQuery = session.createQuery("delete from Empleados where oficinas.codOfi in (select codOfi from Oficinas where localidad = :localidad)");
            eliminarEmpleadosQuery.setParameter("localidad", localidad);
            eliminarEmpleadosQuery.executeUpdate();

            // Eliminar oficinas en la localidad
            Query eliminarOficinasQuery = session.createQuery("delete from Oficinas where localidad = :localidad");
            eliminarOficinasQuery.setParameter("localidad", localidad);
            int oficinasEliminadas = eliminarOficinasQuery.executeUpdate();

            transaction.commit();

            System.out.println("--- Oficinas eliminadas ---");
            System.out.println(oficinasEliminadas + " oficinas eliminadas en la localidad " + localidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
