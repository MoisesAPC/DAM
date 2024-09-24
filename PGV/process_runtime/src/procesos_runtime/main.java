package procesos_runtime;

import java.io.IOException;

public class main {

	public static void main(String[] args) {
		String comando1 = "notepad.exe";
		String comando2 = "calc.exe";
		
		ProcessBuilder pb1 = new ProcessBuilder(comando1);
		Runtime rt1 = Runtime.getRuntime();
		
		// Lanzamos proceso 1 (notepad.exe)
		try {
			Process proc1 = pb1.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Lanzamos proceso 2 (calc.exe)
		try {
			Process proc2 = rt1.exec(comando2);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
