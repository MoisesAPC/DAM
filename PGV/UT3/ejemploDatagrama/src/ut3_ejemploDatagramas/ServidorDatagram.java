package ut3_ejemploDatagramas;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class ServidorDatagram {
	public static void main(String args[]) {

		System.out.println("Arranca el servidor");
		DatagramSocket datagramSocket = null;
		try {
			datagramSocket = new DatagramSocket(25556);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		while (datagramSocket != null) {
			try {
				System.out.println("Esperando mensajes.......");
				byte[] entrada = new byte[4];
				DatagramPacket datagrama1 = new DatagramPacket(entrada, 4);
				datagramSocket.receive(datagrama1);

				String mensaje = new String(datagrama1.getData());
				InetAddress dircliente = datagrama1.getAddress();
				int puertocliente = datagrama1.getPort();
				System.out.println("Mensaje recibido desde: " + dircliente
						+ ", puerto " + puertocliente);
				System.out.println("Mensaje: " + mensaje);

				if (mensaje.equals("hora")) {
					System.out.println("Enviando respuesta");

					Date d = new Date(System.currentTimeMillis());
					byte[] salida = d.toString().getBytes();
					DatagramPacket datagrama2 = new DatagramPacket(salida,
							salida.length, dircliente, puertocliente);
					datagramSocket.send(datagrama2);
					System.out.println("Mensaje enviado");
				} else {
					System.out.println("Esa petición no la conocemos ;-)");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Fin");
	}
}
