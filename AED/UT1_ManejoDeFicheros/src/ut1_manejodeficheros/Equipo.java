package ut1_manejodeficheros;

import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;

public class Equipo implements Serializable {
    private int num;            // Número del club
    private String nombre;      // Nombre del club
    private String presidente;  // Nombre del presidente del club
    private int telefono;       // Número de teléfono
    private String localidad;   // Localidad en la que se encuentra el club

    public static final int tamanoStrings = 100;  // Tamaño en bytes de todas las strings contenidas en la clase (50 caracteres)
    public static final int tamanoBytes = Integer.BYTES + tamanoStrings + tamanoStrings + Integer.BYTES + tamanoStrings;  // Tamaño de la clase en bytes

    public Equipo(int num, String nombre, String presidente, int telefono, String localidad) {
        this.num = num;
        this.nombre = nombre;
        this.presidente = presidente;
        this.telefono = telefono;
        this.localidad = localidad;
    }

    public Equipo() {
        this.num = 0;
        this.nombre = "";
        this.presidente = "";
        this.telefono = 0;
        this.localidad = "";
    }

    public int getNum() {
        return num;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPresidente() {
        return presidente;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "num=" + num +
                ", nombre='" + nombre + '\'' +
                ", presidente='" + presidente + '\'' +
                ", telefono=" + telefono +
                ", localidad='" + localidad + '\'' +
                '}';
    }

    // Dada una línea cargada de fichero, parsea sus parámetros
    public void parsearLinea(String linea, String separador) {
        String[] partes = linea.split(separador);

        try {
            if (partes.length > 0) {
                this.num = Integer.parseInt(partes[0].trim());
                this.nombre = partes[1].trim();
                this.presidente = partes[2].trim();
                this.telefono = Integer.parseInt(partes[3].trim());
                this.localidad = partes[4].trim();
            }
        }
        catch (NumberFormatException e) {
            System.err.println("Error al convertir números: " + e.getMessage());
        }
    }
}
