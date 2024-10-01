package ut1_manejodeficheros;

import java.io.*;
import java.util.ArrayList;

public class ObjetosEquipos {
    public static void guardarEquiposEnFicheroObj(String ficheroRAF, String ficheroObj) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(ficheroRAF, "r");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheroObj));

        while (raf.getFilePointer() < raf.length()) {
            Equipo equipo = FicheroUtils.leerEquipoDeFichero(raf);
            oos.writeObject(equipo);
        }
    }

    public static ArrayList<Equipo> leerEquipoDeFichero(String ficheroObj) throws IOException, ClassNotFoundException {
        ArrayList<Equipo> equipos = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroObj))) {
            while (true) {
                try {
                    Equipo equipo = (Equipo) ois.readObject();
                    equipos.add(equipo);
                }
                // Final del archivo
                catch (EOFException e) {
                    break;
                }
            }
        }

        return equipos;
    }
}
