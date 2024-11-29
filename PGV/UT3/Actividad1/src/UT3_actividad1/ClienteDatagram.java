package UT3_actividad1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteDatagram {
    //public static final String ipServidor = "192.168.1.72";   // IP de casa
    public static final String ipServidor = "192.168.2.90";     // IP de clase
    public static final int puertoServidor = 5678;
    public static DatagramSocket socketCliente = null;     // El socket del cliente
    public static final int maxDatagramPacketSize = 65507;  // Tamaño máximo de un DatagramPacket

    public ClienteDatagram(DatagramSocket socketCliente) {
        this.socketCliente = socketCliente;
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Boolean cerrarCliente = false;

        try {
            System.out.println("Arranca cliente");

            // Creamos el socket del cliente
            socketCliente = new DatagramSocket();

            // Esta es la IP del servidor (se puede averiguar, además, usando ipconfig)
            InetAddress ipServidorInetAddress = InetAddress.getByName(ipServidor);

            while (!cerrarCliente) {
                // Escribimos el mensaje que le vamos a enviar al servidor
                System.out.print("Mensaje: ");
                String mensaje = teclado.nextLine();

                if (mensaje.getBytes().length > maxDatagramPacketSize) {
                    System.out.println("Los datos que se quieren enviar superan el tamaño máximo del paquete");
                }
                else {
                    // Enviamos el paquete al servidor
                    DatagramPacket paquete = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, ipServidorInetAddress, puertoServidor);
                    socketCliente.send(paquete);
                    System.out.println("Mensaje enviado a " + ipServidorInetAddress);
                }

                // Si el usuario escribe el comando "Fin",
                // cerramos el cliente
                if (mensaje.length() >= 3) {
                    String comando = mensaje.substring(0, 3);
                    if (comando.equals("Fin")) {
                        cerrarCliente = true;
                    }
                }
            }

            socketCliente.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
