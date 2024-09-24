package procesos_runtime;

import java.io.IOException;

public class main {

	public static void main(String[] args) {
		// USEFUL STATEMENT FOR KNOWING DE WORKING DIRECTORY FOR THE JAVA EXECUTABLE
		// FOR DEBUGGING RELATIVE PATHS WITHIN THE PROGRAM
		//System.out.println("Current working directory: " + System.getProperty("user.dir"));

		String comando1 = "notepad.exe";
		String comando2 = "calc.exe";
		// Use a relative path directly (assuming the DAM folder is opened on Intellij)
		String fichero_bat = "PGV\\process_runtime\\src\\procesos_runtime\\prueba.bat";
		
		ProcessBuilder pb1 = new ProcessBuilder(comando1);
		ProcessBuilder pb2 = new ProcessBuilder("cmd", "/C", "start " + fichero_bat);
		Runtime rt1 = Runtime.getRuntime();
		Runtime rt2 = Runtime.getRuntime();
		
		// Lanzamos proceso 1 (notepad.exe)
		try {
			Process proc1 = pb1.start();
			Process proc2 = pb2.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Lanzamos proceso 2 (calc.exe)
		try {
			Process proc2 = rt1.exec(comando2);
			Process proc3 = rt2.exec(fichero_bat);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
