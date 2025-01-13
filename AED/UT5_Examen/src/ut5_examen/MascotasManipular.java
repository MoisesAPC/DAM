package ut5_examen;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.Scanner;

public class MascotasManipular {
    // Ruta de la base de datos (relativa al directorio raíz del proyecto)
    final static String baseDeDatos = "connections/bd_mascotas.yap";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Abre la base de datos de mascotas. Si no existe, la crea
        ObjectContainer db = Db4oEmbedded.openFile(
                Db4oEmbedded.newConfiguration(), baseDeDatos);


        // Imprimimos todas las mascotas guardadas en la base de datos
        System.out.println("---- EJ.3) TODAS LAS MASCOTAS ----");
        ObjectSet<Mascotas> objectSet = db.queryByExample(new Mascotas());
        while (objectSet.hasNext()) {
            Mascotas mascota = objectSet.next();
            System.out.println(mascota.toString());
        }


        // Obtenemos el nombre y el teléfono de las mascotas que son "Labrador"
        System.out.println("---- EJ.4a NOMBRE Y TELEFONO, LABRADOR ----");
        objectSet = db.queryByExample(new Mascotas(null, null, "Labrador", null, null));
        // Imprimimos los nombres y los puntos de dichos mascotas
        while (objectSet.hasNext()) {
            Mascotas mascota = objectSet.next();
            System.out.println("--- Nombre: " + mascota.getNombre() + ", Telefono: " + mascota.getTelefono());
        }


        // Modifica al especialista de las mascotas de raza "Pastor Aleman" y asignarles a Florencio.
        objectSet = db.queryByExample(new Mascotas(null, null, "Pastor Aleman", null, null));

        if (objectSet.size() == 0) {
            System.out.println("No existe la mascota");
        }
        else {
            while (objectSet.hasNext()) {
                Mascotas nuevaMascota = objectSet.next();
                nuevaMascota.setVeterinario("Florencio");    //se modifica
                db.store(nuevaMascota);        //se vuelve a guardar
            }
        }


        // Mostrar cuántas mascotas hay de la raza "Pastor Alemán", además del nombre y teléfono del
        // dueño de cada uno.
        System.out.println("---- EJ.4c NOMBRE Y TELEFONO, PASTOR ALEMAN ----");
        objectSet = db.queryByExample(new Mascotas(null, null, "Pastor Aleman", null, null));

        if (objectSet.size() == 0) {
            System.out.println("No existe la mascota");
        }
        else {
            System.out.println("- Numero de mascotas Pastor Aleman: " + objectSet.size());

            while (objectSet.hasNext()) {
                Mascotas mascota = objectSet.next();
                System.out.println("--- Nombre de la mascota: " + mascota.getNombre() + ", Telefono del propietario: " + mascota.getTelefono());
            }
        }

        db.close();
    }
}
