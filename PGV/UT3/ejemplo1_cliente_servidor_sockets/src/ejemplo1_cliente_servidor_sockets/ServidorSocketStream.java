package ejemplo1_cliente_servidor_sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class ServidorSocketStream {
	public static void main(String args[]) throws IOException {

		// Creamos el socket del lado del servidor.
		// Aquí se ve como hacer la conexión asumiendo que el host es siempre "localhost"
		System.out.println("Creando socket del servidor");
		ServerSocket serverSocket = new ServerSocket(5555);

		// Aquí se ve como hacer la conexión especificándole tanto el hostname como el puerto
	/*	
		ServerSocket serverSocket = new ServerSocket();
	
		System.out.println("Realizando el bind");
		InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
		serverSocket.bind(addr);
*/
		// La variable seguir se pone a "false" cuando queremos parar el servidor.
		// El servidor se para cuando el cliente escribe "exit"
		boolean seguir = true;
		System.out.println("Acepta conexiones");

		// Creamos el socket que establecerá la conexión con el cliente
		// El método "accept" espera sin ejecutar el código siguiente hasta que el cliente establezca la conexión
		Socket newSocket = serverSocket.accept();

		System.out.println("Conexion recibida");
		InputStream is = newSocket.getInputStream();

		
		while (seguir) {
			// Mensaje recibido del cliente
			byte[] mensaje = new byte[25];

			is.read(mensaje);
			// Mensaje recibido del cliente convertido a String
			String smens = (new String(mensaje)).trim();
			
			if (!smens.isEmpty()){

				System.out.println("Mensaje recibido: " + smens);
				if (smens.equals("exit"))
				seguir = false;
			}
		}
		System.out.println("Cerramos el socket que escucha al cliente");
		newSocket.close();

		System.out.println("Y cerrando el socket del servidor");
		serverSocket.close();
	}
}
