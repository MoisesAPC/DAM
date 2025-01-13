package ut5_examen;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class MascotasInsertar {
    // Ruta de la base de datos (relativa al directorio raíz del proyecto)
    final static String baseDeDatos = "connections/bd_mascotas.yap";

    public static void main(String[] args) {
        // Creamos registros
        Mascotas mascota1 = new Mascotas("Coty", "11223355", "Labrador", "666111444", "Guaci");
        Mascotas mascota2 = new Mascotas("Max", "88823355", "Pastor Aleman", "928111444", "Laura");
        Mascotas mascota3 = new Mascotas("Luna", "88811166", "Pastor Aleman", "555557788", "Laura");
        Mascotas mascota4 = new Mascotas("Poki", "11111111", "Siamés", "666111444", "Miguel");
        Mascotas mascota5 = new Mascotas("Pepe", "22766745", "Labrador", "928657845", "Antonio");
        Mascotas mascota6 = new Mascotas("Laila", "98235612", "British Shorthair", "928657845", "Azucena");

        // Abre la BD personas, si no existe, la crea
        ObjectContainer db = Db4oEmbedded.openFile(
                Db4oEmbedded.newConfiguration(), baseDeDatos);

        // Insertamos los datos
        db.store(mascota1);
        db.store(mascota2);
        db.store(mascota3);
        db.store(mascota4);
        db.store(mascota5);
        db.store(mascota6);

        //Se cierra la bd
        db.close();
    }
}
