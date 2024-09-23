package prueba1;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		File ficheroEntrada = null;
		FileWriter ficheroSalida =  null;
		Scanner scanner = null;
		
		try {
			ficheroEntrada = new File("C:\\Users\\Alumnadomañana\\Desktop\\prueba.txt");
			ficheroSalida = new FileWriter("C:\\Users\\Alumnadomañana\\Desktop\\prueba_salida.txt");
			scanner = new Scanner(ficheroEntrada);

			while (scanner.hasNextLine()) {
				String linea = scanner.nextLine(); 	// Guardamos la línea en un String
				
				// Lectura de fichero
				OBJ entradaDeFichero = new OBJ(linea);
				entradaDeFichero.print();
				System.out.println("");
				
				// Escritura a fichero
				ficheroSalida.write(entradaDeFichero.formatear());
				ficheroSalida.write("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Excepción durante la ejecución del programa. " + ex.getMessage());
		}
		finally {
			try {
				if (ficheroSalida != null) {
					ficheroSalida.close();
				}
				if (scanner != null) {
					scanner.close();
				}
				
			}
			catch (Exception ex2) {
				System.out.println("Excepción al cerrar objetos. " + ex2.getMessage());
			}
		}
	}
}
