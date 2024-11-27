package UT3_actividad1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteStream {
    public static final String ipServidor = "192.168.1.72";
    public static final int puertoServidor = 5999;
    public static Socket socketCliente = null;     // El socket del cliente
    public static final String hostname = "localhost";

    public ClienteStream(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Boolean cerrarCliente = false;

        try {
            System.out.println("Arranca cliente");

            // Creamos el socket del cliente
            socketCliente = new Socket();

            // Establecemos la conexión
            InetSocketAddress addr = new InetSocketAddress(hostname, puertoServidor);
            socketCliente.connect(addr);

            // Esto son los datos que le vamos a mandar al servidor
            OutputStream outputStream = socketCliente.getOutputStream();

            while (!cerrarCliente) {
                // Escribimos el mensaje que le vamos a enviar al servidor
                System.out.print("Mensaje: ");
                String mensaje = teclado.nextLine();

                // Escribimos los datos en el outputStream.
                // Como la conexión ya está establecida, en cuanto escribamos el mensaje,
                // aparecerá en el servidor
                outputStream.write((mensaje + "\n").getBytes());
                outputStream.flush();

                // Si el usuario escribe el comando "Fin",
                // cerramos el cliente
                String comando = mensaje.substring(0, 3);
                if (comando.equals("Fin")) {
                    cerrarCliente = true;
                }
            }

            socketCliente.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
