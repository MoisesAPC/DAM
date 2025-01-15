package ut3_examen;

import java.io.IOException;
import java.net.*;
import java.util.Random;

/**
 * El servidor creará un hilo por paquete recibido del cliente
 */
public class Servidor extends Thread {
    public static final int puertoServidor = 25676;  // El puerto por donde va a escuchar el servidor
    public static DatagramSocket socketServidor = null;  // El socket del servidor
    private static DatagramPacket paqueteCliente = null;  // El paquete que llegó al servidor desde el cliente
    public static final int maxDatagramPacketSize = 65507;  // Tamaño máximo de un DatagramPacket

    private static Boolean servidorEnEjecucion = true;

    // El mensaje que se recibió del cliente cuando este se conectó al servidor
    // Puede ser: "Enviar FTP" o bien "Enviar Where"
    private static String mensajeRecibidoDelCliente = "";

    public Servidor(DatagramSocket socketServidor) {
        this.socketServidor = socketServidor;
    }

    public static void setPaqueteCliente(DatagramPacket paqueteCliente_) {
        paqueteCliente = paqueteCliente_;
    }

    public static void main(String args[]) {
        try {
            servidorEnEjecucion = true;

            System.out.println("Arranca el servidor UDP");

            // Creamos el socket del servidor
            socketServidor = new DatagramSocket(puertoServidor);

            while (servidorEnEjecucion) {
                // Creamos el paquete con el que se recibirán los datos de los clientes
                byte[] respuestaCliente = new byte[maxDatagramPacketSize];
                DatagramPacket respuestaClientePaquete = new DatagramPacket(respuestaCliente, respuestaCliente.length);

                // Esperamos hasta recibir datos en el socket
                // Cuando recibamos el mensaje, creamos un nuevo hilo que maneje dicho mensaje
                socketServidor.receive(respuestaClientePaquete);
                setPaqueteCliente(respuestaClientePaquete);

                mensajeRecibidoDelCliente = new String(respuestaClientePaquete.getData(), 0, respuestaClientePaquete.getLength());
                // Le ponemos un nombre al hilo que escuchará al cliente y arrancamos el hilo
                Servidor hilo = new Servidor(socketServidor);
                hilo.start();
            }

            socketServidor.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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

                // Guardo la IP y el puerto del cliente que envió el mensaje
                InetAddress ipCliente = paqueteCliente.getAddress();
                int puertoCliente = paqueteCliente.getPort();
                String mensajeAEnviarACliente = "";

                // Si recibo el mensaje "Enviar FTP" del cliente, entonces al cliente le enviaré el mensaje de que
                // se han subido X archivos
                if (mensajeRecibidoDelCliente.equalsIgnoreCase("Enviar FTP")) {
                    Random random = new Random();
                    int numArchivos = random.nextInt(100) + 1;
                    mensajeAEnviarACliente = "Se han subido correctamente " + numArchivos + " archivos";
                } else if (mensajeRecibidoDelCliente.equalsIgnoreCase("Enviar Where")) {
                    mensajeAEnviarACliente = "Está ud conectado por el puerto " + puertoCliente;
                }

                // Enviamos el paquete al cliente
                DatagramPacket paquete = new DatagramPacket(mensajeAEnviarACliente.getBytes(), mensajeAEnviarACliente.getBytes().length, ipCliente, puertoCliente);
                socketServidor.send(paquete);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
