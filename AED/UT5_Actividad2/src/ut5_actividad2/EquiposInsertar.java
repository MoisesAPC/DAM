package ut5_actividad2;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class EquiposInsertar {
    // Ruta de la base de datos (relativa al directorio raíz del proyecto)
    final static String baseDeDatos = "connections/bdequipos.yap";

    public static void main(String[] args) {
        // Creamos registros
        Equipos equipo1 = new Equipos("FC Barcelona", "Primera División", 1, "Barcelona, España", "Joan Laporta", 80);
        Equipos equipo2 = new Equipos("Real Madrid", "Primera División", 1, "Madrid, España", "Florentino Pérez", 82);
        Equipos equipo3 = new Equipos("Manchester United", "Premier League", 2, "Manchester, Inglaterra", "Joel Glazer", 75);
        Equipos equipo4 = new Equipos("Bayern Munich", "Bundesliga", 2, "Múnich, Alemania", "Herbert Hainer", 85);
        Equipos equipo5 = new Equipos("Juventus", "Serie A", 3, "Turín, Italia", "Andrea Agnelli", 78);
        Equipos equipo6 = new Equipos("Paris Saint-Germain", "Ligue 1", 3, "París, Francia", "Nasser Al-Khelaifi", 90);

        // Abre la BD personas, si no existe, la crea
        ObjectContainer db = Db4oEmbedded.openFile(
                Db4oEmbedded.newConfiguration(), baseDeDatos);

        // Insertamos los datos
        db.store(equipo1);
        db.store(equipo2);
        db.store(equipo3);
        db.store(equipo4);
        db.store(equipo5);
        db.store(equipo6);

        //Se cierra la bd
        db.close();
    }
}
