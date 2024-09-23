import java.io.File;
import java.util.Scanner;

public class apartadoA {
	
	private static void mostrarDirectorio(String dir) {
        try {
            // Current directory --> "."
            File f = new File(dir); 
  
            // Get all the names of the files present 
            // in the given directory 
            String[] files = f.list(); 
            
            // Display the names of the files
            System.out.println("Ficheros en el directorio:");
            for (int i = 0; i < files.length; i++) { 
                System.out.println(files[i]); 
            } 
        } 
        catch (Exception e) { 
            System.err.println(e.getMessage()); 
        }
	}
	
	public static void main(String[] args) {
		// Apartado 1
		mostrarDirectorio(".");
		
		// Apartado 2
		Scanner teclado = new Scanner(System.in);
		
		System.out.print("Introduce un directorio: ");
		String dir = teclado.nextLine();
		
		mostrarDirectorio(dir);
				
		//teclado.close();
	}
}
