package raf_prueba2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;

public class Empleado implements Serializable {
    private int numEmpleado;
    private String nombre;
    private String telefono;
    private double salario;

    private final static int tamanoNombre = 25;	    // Tamaño máximo del nombre, en bytes
    private final static int tamanoTelefono = 9;	// Tamaño máximo del teléfono, en bytes
    private final static int tamanoRegistro = Integer.BYTES + (tamanoNombre * Character.BYTES) + (tamanoTelefono * Character.BYTES) + Double.BYTES;

    // Lista con todos los empleados
    private static ArrayList<Empleado> list = new ArrayList<>();

    public Empleado(int numEmpleado, String nombre, String telefono, double salario) {
        this.numEmpleado = numEmpleado;
        this.nombre = nombre;
        this.telefono = telefono;
        this.salario = salario;

        // Añadimos el nuevo estudiante a la lista
        list.add(this);
    }

    public static ArrayList<Empleado> getList() {
        return list;
    }

    public static void setList(ArrayList<Empleado> list) {
        Empleado.list = list;
    }

    public static int getTamanoRegistro() {
        return tamanoRegistro;
    }

    public static int getTamanoNombre() {
        return tamanoNombre;
    }

    public static int getTamanoTelefono() {
        return tamanoTelefono;
    }

    public int getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(int numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "numEmpleado=" + numEmpleado +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", salario=" + salario +
                '}';
    }

    public static String leerStringDeFichero(int longitud, RandomAccessFile fichero) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < longitud; i++) {
            char c = fichero.readChar(); // Cada char son 2 bytes
            sb.append(c);
        }
        sb.setLength(longitud);
        return sb.toString().trim();
    }

    public static void escribirStringEnFichero(String str, int longitudEnBytes, RandomAccessFile fichero) throws IOException {
        for (int i = 0; i < longitudEnBytes; i++) {
            if (i < str.length()) {
                fichero.writeChar(str.charAt(i));
            }
            else {
                fichero.writeChar('\u0000');
            }
        }
    }

    public static Empleado leerEmpleadoDeFichero(RandomAccessFile fichero) throws IOException {
        int numEmpleado = fichero.readInt();
        String nombre = leerStringDeFichero(tamanoNombre, fichero);
        String telefono = leerStringDeFichero(tamanoTelefono, fichero);
        double salario = fichero.readDouble();

        return new Empleado(numEmpleado, nombre, telefono, salario);
    }

    public static void escribirEmpleadoEnFichero(RandomAccessFile fichero, Empleado empleado) throws IOException {
        fichero.writeInt(empleado.getNumEmpleado());
        escribirStringEnFichero(empleado.getNombre(), tamanoNombre, fichero);
        escribirStringEnFichero(empleado.getTelefono(), tamanoTelefono, fichero);
        fichero.writeDouble(empleado.getSalario());
    }

    public static Empleado buscarEmpleado(int numEmpleado, RandomAccessFile fichero) {
        try {
            fichero.seek(0);

            while (fichero.getFilePointer() < fichero.length()) {
                Empleado empleado = leerEmpleadoDeFichero(fichero);
                if (empleado.getNumEmpleado() == numEmpleado) {
                    return empleado;
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Empleado buscarEmpleadoPorIndiceLista(int indiceLista, RandomAccessFile fichero) throws IOException {
        long posicion = (long) indiceLista * tamanoRegistro;

        fichero.seek(posicion);
        if (posicion < 0 || posicion >= fichero.length()) {
            System.out.println("Error: Índice del empleado fuera de fichero");
            return null;
        }
        else {
            return leerEmpleadoDeFichero(fichero);
        }
    }

    public static int buscarIndiceDeEmpleado(int numEmpleado, RandomAccessFile fichero) {
        try {
            int indiceEmpleado = 0;
            fichero.seek(0);

            while (fichero.getFilePointer() < fichero.length()) {
                Empleado empleado = leerEmpleadoDeFichero(fichero);
                if (empleado.getNumEmpleado() == numEmpleado) {
                    return indiceEmpleado;
                }
                indiceEmpleado++;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public static void guardarEmpleadoEnListaPorIndice(int indiceLista, Empleado empleado, RandomAccessFile fichero) throws IOException {
        long posicion = (long) indiceLista * tamanoRegistro;

        if (posicion < 0 || posicion >= fichero.length()) {
            System.out.println("Error: Índice del empleado fuera de fichero");
            return;
        }

        fichero.seek(posicion);

        escribirEmpleadoEnFichero(fichero, empleado);
    }
}
