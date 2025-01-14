package ut5_actividad2;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.Scanner;

public class EquiposManipular {
    // Ruta de la base de datos (relativa al directorio raíz del proyecto)
    final static String baseDeDatos = "connections/bdequipos.yap";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Abre la base de datos de equipos. Si no existe, la crea
        ObjectContainer db = Db4oEmbedded.openFile(
                Db4oEmbedded.newConfiguration(), baseDeDatos);

        //ObjectSet<Equipos> p = db.queryByExample(new Equipos(null, "Primera División", 0, null, null, 0));
        ObjectSet<Equipos> objectSet = db.queryByExample(new Equipos());
        System.out.println("- Numero de equipos: "+ objectSet.size());

        // Imprimimos todos los equipos guardados en la base de datos
        while (objectSet.hasNext()) {
            Equipos equipo = objectSet.next();
            System.out.println(equipo.toString());
        }

        // Obtener el nombre y los puntos de los equipos de la categoría "alevin"
        // Obtenemos aquellos equipos cuya categoría sea "alevin"
        objectSet = db.queryByExample(new Equipos(null, "alevin", 0, null, null, 0));

        // Imprimimos los nombres y los puntos de dichos equipos
        while (objectSet.hasNext()) {
            Equipos equipo = objectSet.next();
            System.out.println("--- Nombre: " + equipo.getNombre() + ", Puntos: " + equipo.getPuntos());
        }

        // Obtener la sede y el presidente de un equipo solicitado
        // Preguntamos por el nombre del equipo
        System.out.print("- Introduce el nombre del equipo: ");
        String nombreEquipoABuscar = teclado.nextLine();

        // Obtenemos el equipo dado su nombre
        objectSet = db.queryByExample(new Equipos(nombreEquipoABuscar, null, 0, null, null, 0));

        // Imprimimos la sede y el presidente de dicho equipo
        while (objectSet.hasNext()) {
            Equipos equipo = objectSet.next();
            System.out.println("--- Sede: " + equipo.getSede() + ", Presidente: " + equipo.getPresidente());
        }

        /*
        // Consulta de las equipos que están en primera división
        objectSet = db.queryByExample(new Equipos(null, "Primera División", 0, null, null, 0));
        System.out.println("Numero de equipos en primera division: " + objectSet.size());

        // Imprimimos solamente los equipos en primera división
        while (objectSet.hasNext()) {
            Equipos equiposEnPrimeraDivision = objectSet.next();
            System.out.println(equiposEnPrimeraDivision.toString());
        }

        // Se modifica el presidente del equipo cuyo nombre es "FC Barcelona"
        objectSet = db.queryByExample(new Equipos("FC Barcelona", null, 0, null, null, 0));
        if (objectSet.size() == 0) {
            System.out.println("No existe el equipo con el nombre especificado");
        }
        else {
            // Nuevo presidente: Pepe Martín
            Equipos nuevoEquipo = objectSet.next();
            nuevoEquipo.setPresidente("Pepe Martín");	//se modifica
            db.store(nuevoEquipo);		//se vuelve a guardar
        }

        // Para eliminar los equipos que tengan 85 puntos
        objectSet = db.queryByExample(new Equipos(null, null, 0, null, null, 85));
        if (objectSet.size() == 0) {
            System.out.println("No existe el equipo con la puntuación especificada");
        }
        else {
            Equipos equipoAEliminar = objectSet.next();
            db.delete(equipoAEliminar);		//se borra
        }
        */
        db.close();
    }
}
