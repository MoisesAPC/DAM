package UT3_actividad1;

import java.io.IOException;
import java.net.*;
import java.util.Date;

public class ServidorDatagram extends Thread {
    public static final int puerto = 5678;
    public static DatagramSocket socket = null;
    public static final int maxDatagramPacketSize = 65507;  // Tamaño máximo de un DatagramPacket

    public ServidorDatagram(DatagramSocket socket) {
        this.socket = socket;
    }

    // Imprime la IP y el puerto del cliente (una vez se han recibido sus datos)
    private void imprimirInfoDelCliente(DatagramPacket paquete) {
        InetAddress ipCliente = paquete.getAddress();
        int puertoCliente = paquete.getPort();
        System.out.println("Mensaje recibido desde la IP " + ipCliente + ", con puerto " + puertoCliente);
    }

    public static void main(String args[]) {
        System.out.println("Arranca el servidor DATAGRAM");

        try {
            socket = new DatagramSocket(puerto);
            new ServidorDatagram(socket).start();
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            /**
             * @note Creamos distintos paquetes solamente porque los tamaños de los datos recibidos del cliente
             * pueden ser distintos.
             *
             * Si no son distintos, se puede reutilizar el mismo socket.
             */
            while (true) {
                // Creamos el paquete con el que se recibirán los datos del cliente
                byte[] entradaCliente = new byte[maxDatagramPacketSize];
                DatagramPacket paquete = new DatagramPacket(entradaCliente, entradaCliente.length);

                // Esperamos hasta recibir datos en el socket
                socket.receive(paquete);

                // Guardo la IP y el puerto del cliente que envió el mensaje
                InetAddress ipCliente = paquete.getAddress();
                int puertoCliente = paquete.getPort();
                imprimirInfoDelCliente(paquete);

                // Imprimimos el mensaje recibido
                String mensaje = new String(paquete.getData());
                System.out.println("Mensaje: " + mensaje);

                if (mensaje.equals("hora")) {
                    System.out.println("Enviando respuesta");

                    Date d = new Date(System.currentTimeMillis());
                    byte[] salida = d.toString().getBytes();
                    DatagramPacket datagrama2 = new DatagramPacket(salida,
                            salida.length, ipCliente, puertoCliente);
                    socket.send(datagrama2);
                    System.out.println("Mensaje enviado");
                }
                else {
                    System.out.println("Esa petición no la conocemos ;-)");
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
