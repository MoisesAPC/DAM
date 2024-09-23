import java.util.Scanner;

public class main {
	
	private static int manejoDeOpciones(int opcion) {
		switch (opcion) {
			case 1:
				apartadoA.main(null);
				return 0;
			case 2:
				apartadoB.main(null);
				return 0;
			case 7:
				return -1;
			default:
				System.out.println("Opción inválida");
				return 1;
		}
	}

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		
		do {
			System.out.println("1. Apartado A");
			System.out.println("2. Apartado B");
			System.out.println("3. Apartado C");
			System.out.println("4. Apartado D");
			System.out.println("5. Apartado E");
			System.out.println("6. Apartado F");
			System.out.println("7. Salir");
			
			System.out.print("Opción: ");
			int opcion = teclado.nextInt();
			
			if (manejoDeOpciones(opcion) == -1) {
				break;
			}
		} while (true);
		
		teclado.close();
	}
}
