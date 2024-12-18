package ut5_actividad1_eclipse;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class EjemploDb4o {
	public static void main(String[] args) {

		// Creamos registros
		Personas p1 = new Personas(11, "42222222", "Ana", "Ronda");
		Personas p2 = new Personas(12, "43333333", "Jose", "Arucas");
		Personas p3 = new Personas(13, "45555555", "Luis", "Arucas");
		Personas p4 = new Personas(14, "48888888", "Jorge", "Arucas");
		Personas p5 = new Personas(15, "47777777", "Luisa", "Telde");
		Personas p6 = new Personas(16, "49999999", "Pepa", "Telde");

		// Abre la BD personas, si no existe, la crea
		ObjectContainer db = Db4oEmbedded.openFile(
				Db4oEmbedded.newConfiguration(), "bdpersonas.yap");

		db.store(p1);
		db.store(p2);
		db.store(p3);
		db.store(p4);
		db.store(p5);
		db.store(p6);
		
		//Se cierra la bd
		db.close();

	}
}
