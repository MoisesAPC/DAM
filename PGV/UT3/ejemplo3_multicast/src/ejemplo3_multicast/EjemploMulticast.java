package ejemplo3_multicast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

//Clases para la utilizaci�n de Multicast
//MulticastSocket()
//joinGroup()
//leaveGroup()
//send()
//receive()

public class EjemploMulticast {
	public static void main(String args[]) throws IOException {
		String direccion="225.1.1.1";		
		int puerto=6999;

		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		MulticastSocket m=new MulticastSocket();
		InetAddress grupo=InetAddress.getByName(direccion);
		String cad="";
		System.out.println("El servidor: "+InetAddress.getLocalHost()+" conectado para enviar mensajes");
		
		while (!cad.trim().equals("/")) {
			System.out.println("Datos para enviar: ");
			cad=br.readLine();
			DatagramPacket paquete=new DatagramPacket(cad.getBytes(), cad.length(), grupo, puerto);
			m.send(paquete);
		}
		m.close();
		System.out.println("Socket cerrado");
	}
}
