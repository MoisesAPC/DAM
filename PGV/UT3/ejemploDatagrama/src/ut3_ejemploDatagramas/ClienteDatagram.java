package ut3_ejemploDatagramas;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClienteDatagram {
	public static void main(String args[]) {
		
		try {
			System.out.println("Creando datagrama cliente");

			DatagramSocket datagramSocket = new DatagramSocket();

			// Esta es la IP del servidor (se puede averiguar, además, usando ipconfig)
			InetAddress dirservidor = InetAddress.getByName("192.168.2.90");
						
			String mensaje = new String("hora");
			DatagramPacket datagrama1 = new DatagramPacket(mensaje.getBytes(),
					mensaje.getBytes().length, dirservidor, 25556);
			datagramSocket.send(datagrama1);

			System.out.println("Mensaje enviado a " + dirservidor);

			byte[] respuesta = new byte[100];
			DatagramPacket datagrama2 = new DatagramPacket(respuesta,
					respuesta.length);
			datagramSocket.receive(datagrama2);

			System.out.println("Mensaje recibido: " + new String(respuesta));
			datagramSocket.close();
			System.out.println("el cliente terminó");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
