package procesos_prueba;

import java.io.IOException;

public class main {

	public static void main(String[] args) {
		// USEFUL STATEMENT FOR KNOWING DE WORKING DIRECTORY FOR THE JAVA EXECUTABLE
		// FOR DEBUGGING RELATIVE PATHS WITHIN THE PROGRAM
		//System.out.println("Current working directory: " + System.getProperty("user.dir"));

		// Use a relative path directly (assuming the DAM folder is opened on Intellij)
		String fichero_bat = "PGV\\procesos_prueba\\src\\procesos_prueba\\prueba.bat";

		try {
			// Create ProcessBuilder with the command and relative path
			ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "start", fichero_bat);

			// Set the working directory to the project root
			pb.directory(new java.io.File("."));

			// Start the process
			Process proceso = pb.start();

			System.out.println("Process started successfully");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}