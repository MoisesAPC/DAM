package procesos_prueba;

import java.io.IOException;

public class main {

	public static void main(String[] args) {
		/*
		String comando = "notepad.exe";
		
		// Lanza otro proceso ("notepad.exe") que es hijo de este .java
		try {
			ProcessBuilder pb = new ProcessBuilder(comando);
			Process proceso = pb.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		String fichero_bat = "src\\procesos_prueba\\prueba.bat";
		
		try {
			ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "start " + fichero_bat);
			Process proceso = pb.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
