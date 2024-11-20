package ejemplo1_cliente_servidor_sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSocketStream {
	public static void main(String args[]) throws IOException {
		Scanner sc=new Scanner(System.in);
		System.out.println("Hola, escribe tu nombre: ");
		// El nombre de la persona es la String variable que se va a enviar desde el cliente al servidor
		String myname=sc.nextLine();
		
		// Array de mensajes
		// El último mensaje es "exit" para que el servidor se pare cuando reciba dicho mensaje del cliente
		String[] vecmens = { "Hi 1", "Simulo el 2", "exit" };
		System.out.println("Creando nuevo socket cliente");

		// Socket del lado del cliente. Le ponemos el hostname "localhost" y el puerto 5555
		Socket clientSocket = new Socket();
		System.out.println("Estableciendo la conexion");
		InetSocketAddress addr = new InetSocketAddress("localhost", 5555);

		clientSocket.connect(addr);

		// El InputStream está aquí para comprobar si el usuario ha pulsado "Q" para cerrar el cliente
		InputStream is = clientSocket.getInputStream();

		// Con el OutputStream, le mandamos datos al servidor
		OutputStream os = clientSocket.getOutputStream();

		for (int i = 0; i < vecmens.length; i++) {
			System.out.println("Enviando mensaje " + i);
			os.write((myname+"=> "+vecmens[i]).getBytes());
			System.out.println("Mensaje enviado");
		}

		// Mantenemos el socket del cliente abierto hasta pulsar "Q"
		System.out.println("Escribe Q para salir");
		while (!(sc.nextLine()).equalsIgnoreCase("Q")) {};

		clientSocket.close();
	}
}
