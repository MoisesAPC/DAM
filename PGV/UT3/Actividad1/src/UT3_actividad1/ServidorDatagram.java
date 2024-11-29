package UT3_actividad1;

import java.io.IOException;
import java.net.*;
import java.util.Date;

/**
 * El servidor creará un hilo por paquete recibido del cliente
 */
public class ServidorDatagram extends Thread {
    public static final int puerto = 5678;  // El puerto por donde va a escuchar el servidor
    public static DatagramSocket socketServidor = null;  // El socket del servidor
    private static DatagramPacket paqueteCliente = null;  // El paquete que llegó al servidor desde el cliente
    public static final int maxDatagramPacketSize = 65507;  // Tamaño máximo de un DatagramPacket

    private static Boolean terminarServidor = false;

    public ServidorDatagram(DatagramSocket socketServidor) {
        this.socketServidor = socketServidor;
    }

    public static void setPaqueteCliente(DatagramPacket paqueteCliente_) {
        paqueteCliente = paqueteCliente_;
    }

    public static void main(String args[]) {
        try {
            System.out.println("Arranca el servidor DATAGRAM");

            // Creamos el socket del servidor
            socketServidor = new DatagramSocket(puerto);

            while (!terminarServidor) {
                // Creamos el paquete con el que se recibirán los datos de los clientes
                byte[] respuestaCliente = new byte[maxDatagramPacketSize];
                DatagramPacket respuestaClientePaquete = new DatagramPacket(respuestaCliente, respuestaCliente.length);

                // Esperamos hasta recibir datos en el socket
                // Cuando recibamos el mensaje, creamos un nuevo hilo que maneje dicho mensaje
                socketServidor.receive(respuestaClientePaquete);
                setPaqueteCliente(respuestaClientePaquete);

                // Le ponemos un nombre al hilo que escuchará al cliente y arrancamos el hilo
                ServidorDatagram hilo = new ServidorDatagram(socketServidor);
                hilo.start();
            }

            socketServidor.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        /**
         * @note Creamos distintos paquetes solamente porque los tamaños de los datos recibidos del cliente
         * pueden ser distintos.
         *
         * Si no son distintos, se puede reutilizar el mismo socket.
         */

        // Guardo la IP y el puerto del cliente que envió el mensaje,
        // y los imprimo por pantalla
        InetAddress ipCliente = paqueteCliente.getAddress();
        int puertoCliente = paqueteCliente.getPort();
        System.out.println("Mensaje recibido desde la IP " + ipCliente + ", con puerto " + puertoCliente);

        // Muestro el nombre del hilo que ejecuta el servidor, así como la hora en la que se realizó la conexión
        Date fechaConexionRealizada = new Date(System.currentTimeMillis());
        String nombreHiloCliente = Thread.currentThread().getName();
        System.out.println("--- Nombre del hilo en el que se maneja el paquete recibido del cliente: " + nombreHiloCliente);
        System.out.println("--- Fecha en la que se recibió el paquete del cliente: " + fechaConexionRealizada.toString());

        // Imprimimos el mensaje recibido (le quitamos los caracteres nulos con el trim y el replaceAll)
        String mensaje = new String(paqueteCliente.getData()).trim().replaceAll("\u0000", "");
        System.out.println("- Mensaje recibido del cliente: " + mensaje);

        // Cada comando se compone de las 3 primeras letras del mensaje.
        // Ejemplo: "Nom: Moisés"
        // "Nom" es el comando
        // "Moisés" es el argumento
        String comando = "";
        String argumento = "";

        if (mensaje.length() >= 3) {
            comando = mensaje.substring(0, 3);
            // @note El comando "Fin" no tiene argumentos
            if (!comando.equals("Fin")) {
                if (mensaje.length() > 4) {
                    argumento = mensaje.substring(4, mensaje.length());
                }
            }
        }

        switch (comando) {
            case "Nom":
                System.out.println("¡Hola, " + argumento + "!");
                break;

            case "Eco":
                System.out.println("Línea OK, " + argumento);
                break;

            case "Fin":
                Date fechaDesconexion = new Date(System.currentTimeMillis());
                System.out.println("El cliente con IP " + ipCliente + " y puerto " + puertoCliente + " se desconecta. Fecha de desconexión: " + fechaDesconexion);
                terminarServidor = true;
                break;

            default:
                System.out.println("Mensaje recibido desconocido");
                break;
        }
    }
}
