package raf_prueba2;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        // Se asume que el directorio raíz es `RandomAccesFile_prueba2`
        final String ficheroDatos = "input_files/datos.dat";
        Scanner teclado = new Scanner(System.in);

        try {
            // Apartado Objetivo)
            System.out.println("--------- Apartado Objetivo ---------");
            FileOutputStream fos = new FileOutputStream(ficheroDatos);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            new Empleado(546, "Juan Pérez", "6192345678", 35000.00);
            new Empleado(4465, "María García", "4085551234", 42000.50);
            new Empleado(54, "Carlos Rodríguez", "7185550123", 38500.75);
            new Empleado(99, "Ana Martínez", "2139876543", 45000.00);
            new Empleado(8, "Luis Sánchez", "5038765432", 39750.25);
            new Empleado(656, "Elena Fernández", "3024567890", 41200.00);
            new Empleado(5, "Pablo Gómez", "4157639821", 36800.50);
            new Empleado(678, "Sofia López", "2025550198", 43500.75);

            RandomAccessFile raf = new RandomAccessFile(ficheroDatos, "rw");
            Empleado.escribirEmpleadoEnFichero(raf, new Empleado(546, "Juan Pérez", "6192345678", 35000.00));
            Empleado.escribirEmpleadoEnFichero(raf, new Empleado(4465, "María García", "4085551234", 42000.50));
            Empleado.escribirEmpleadoEnFichero(raf, new Empleado(54, "Carlos Rodríguez", "7185550123", 38500.75));
            Empleado.escribirEmpleadoEnFichero(raf, new Empleado(99, "Ana Martínez", "2139876543", 45000.00));
            Empleado.escribirEmpleadoEnFichero(raf, new Empleado(8, "Luis Sánchez", "5038765432", 39750.25));
            Empleado.escribirEmpleadoEnFichero(raf, new Empleado(656, "Elena Fernández", "3024567890", 41200.00));
            Empleado.escribirEmpleadoEnFichero(raf, new Empleado(678, "Sofia López", "2025550198", 43500.75));
            raf.close();

            // Apartado 2)
            System.out.println("--------- Apartado 2 ---------");
            System.out.print("Introduzca número de empleado: ");
            int numEmpleado = 0;
            try {
                numEmpleado = teclado.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes de introducir un número");
            }

            RandomAccessFile raf2 = new RandomAccessFile(ficheroDatos, "rw");
            Empleado empleado = Empleado.buscarEmpleado(numEmpleado, raf2);

            if (empleado != null) {
                System.out.println(empleado.toString());
            }
            else {
                System.out.println("Empleado no encontrado");

                teclado.close();
                raf2.close();
                return;
            }

            // Apartado 3)
            System.out.println("--------- Apartado 3 ---------");
            System.out.print("Introduzca número de empleado: ");
            numEmpleado = 0;
            try {
                numEmpleado = teclado.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes de introducir un número");

                teclado.close();
                raf2.close();
                return;
            }

            System.out.print("Introduzca la cantidad extra a sumar al salario: ");
            double cantidadExtra = 0;
            try {
                cantidadExtra = teclado.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes de introducir un número");

                teclado.close();
                raf2.close();
                return;
            }

            int indiceEmpleado = Empleado.buscarIndiceDeEmpleado(numEmpleado, raf2);

            if (indiceEmpleado == -1) {
                System.out.println("Empleado no encontrado");

                teclado.close();
                raf2.close();
                return;
            }

            empleado = Empleado.buscarEmpleadoPorIndiceLista(indiceEmpleado, raf2);
            empleado.setSalario(empleado.getSalario() + cantidadExtra);
            empleado.guardarEmpleadoEnListaPorIndice(indiceEmpleado, empleado, raf2);

            empleado = Empleado.buscarEmpleadoPorIndiceLista(indiceEmpleado, raf2);
            System.out.println(empleado.toString());

            // Apartado 4)
            System.out.println("--------- Apartado 4 ---------");

            empleado = Empleado.buscarEmpleadoPorIndiceLista(4 - 1, raf2);  // 4 - 1, porque el índice empieza en 0
            System.out.println("Teléfono del 4o empleado = " + empleado.getTelefono());

            teclado.close();
            raf2.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
