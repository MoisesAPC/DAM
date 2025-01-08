package ut3_actividad_evaluacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Date;

public class ServidorStream extends Thread {
    public static final int puerto = 5999;  // El puerto por donde va a escuchar el servidor
    public static Socket socket = null;  // El socket del servidor
    private static boolean terminarServidor = false;

    public ServidorStream(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Arranca el servidor STREAM");

        // Creamos el socket del servidor
        ServerSocket socketServidor = new ServerSocket(puerto);

        while (!terminarServidor) {
            try {
                // Esperamos a que un cliente establezca la conexión con el servidor
                Socket socketClienteNuevo = socketServidor.accept();

                ServidorStream hilo = new ServidorStream(socketClienteNuevo);
                hilo.start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Cerramos el socket del servidor
        if (socketServidor != null && !socketServidor.isClosed()) {
            socketServidor.close();
        }
    }

    public void run() {
        try {
            // Guardo la IP y el puerto del cliente que envió el mensaje,
            // y los imprimo por pantalla
            InetAddress ipCliente = socket.getInetAddress();
            int puertoCliente = socket.getPort();
            System.out.println("Mensaje recibido desde la IP " + ipCliente + ", con puerto " + puertoCliente);

            // Muestro el nombre del hilo que ejecuta el servidor, así como la hora en la que se realizó la conexión
            Date fechaConexionRealizada = new Date(System.currentTimeMillis());
            String nombreHiloCliente = Thread.currentThread().getName();
            System.out.println("--- Nombre del hilo en el que se maneja el paquete recibido del cliente: " + nombreHiloCliente);
            System.out.println("--- Fecha en la que se recibió el paquete del cliente: " + fechaConexionRealizada.toString());

            // Obtenemos los mensajes que el cliente nos envió
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while (!terminarServidor) {
                String mensaje = bufferedReader.readLine();
                String comando = "";
                String argumento = "";

                // Cada comando se compone de las 3 primeras letras del mensaje.
                // Ejemplo: "Nom: Moisés"
                // "Nom" es el comando
                // "Moisés" es el argumento
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
                        //socket.close();
                        break;

                    default:
                        System.out.println("Mensaje recibido desconocido");
                        break;
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
