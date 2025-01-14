package ut3_actividad2;

import ut3_actividad_evaluacion.ClienteStream;
import ut3_actividad_evaluacion.ServidorStream;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Esta clase se corresponde con el cliente. El cliente se conecta a un grupo
 * junto con otros clientes
 */
public class Chat extends Thread {
    // Dirección IP del GRUPO. Todos los clientes se conectan a este grupo
    public static String direccionIPGrupo = "225.1.1.1";
    public static final String ipPrivada = "192.168.1.72";   // IP de casa
    //public static final String ipPrivada = "192.168.2.90";     // IP de clase
    public static int puerto = 6999;
    public static int puertoPrivado = 7668;
    public static MulticastSocket socketMulticast = null;
    public static InetSocketAddress grupo = null;
    public static NetworkInterface netIf = null;

    public static String nombre = "";

    private static ServidorStream servidorPrivado = null;

    // Con esta variable indico que el cliente está ejecutandose.
    // Si se pone a false, el cliente se desconecta
    private static boolean enEjecucion = true;

    // El tipo de hilo (1 = Enviar, 2 = Recibir)
    public int tipoHilo = -1;
    public static final int HILO_TIPO_ENVIAR  = 1;
    public static final int HILO_TIPO_RECIBIR = 2;

    public Chat(int tipoHilo) {
        this.tipoHilo = tipoHilo;
    }

    private static void pedirCredenciales() {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Introduce tu nombre: ");
        nombre = teclado.nextLine();
    }

    public static void main(String[] args) throws IOException {
        enEjecucion = true;

        // Introducimos el nombre del usuario
        pedirCredenciales();

        // Configuramos el socket multicast y nos conectamos al grupo
        socketMulticast = new MulticastSocket(puerto);
        InetAddress dir = InetAddress.getByName(direccionIPGrupo);
        grupo = new InetSocketAddress(dir, puerto);
        netIf = NetworkInterface.getByInetAddress(dir);
        socketMulticast.joinGroup(grupo, netIf);

        System.out.println(nombre + " (" + InetAddress.getLocalHost() + ")" + " se ha unido al grupo");

        // Lanzamos ambos hilos, el de enviar datos y el de recibir datos
        Chat hiloEnviar = new Chat(HILO_TIPO_ENVIAR);
        Chat hiloRecibir = new Chat(HILO_TIPO_RECIBIR);

        hiloEnviar.start();
        hiloRecibir.start();

        // Arrancamos el servidor usado para los mensajes privados
        ServerSocket socketServidor = new ServerSocket(puerto);
        // Esperamos a que un cliente establezca la conexión con el servidor
        Socket socketClienteNuevo = socketServidor.accept();

        servidorPrivado = new ServidorStream(socketClienteNuevo);
        servidorPrivado.start();

        // Esperamos a que los hilos terminen
        try {
            hiloEnviar.join();
            hiloRecibir.join();

            socketMulticast.leaveGroup(grupo, netIf);
            socketMulticast.close();
            System.out.println("Socket cerrado");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dado que debemos de ejecutar dos hilos, en el método "run()" del Chat,
     * escogemos qué sub-función "run()" ejecutar (enviar o recibir)
     */
    @Override
    public void run() {
        try {
            switch (tipoHilo) {
                case HILO_TIPO_ENVIAR:
                    runEnviar();
                    break;

                case HILO_TIPO_RECIBIR:
                    runRecibir();
                    break;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método se encarga de escuchar y recibir los mensajes del grupo a tiempo real
     */
    private void runRecibir() throws IOException {
        final int tamanoBufferMensaje = 1000;
        String mensaje;
        byte[] bufferMensajeRecibido = new byte[tamanoBufferMensaje];

        while (enEjecucion) {
            DatagramPacket paquete = new DatagramPacket(bufferMensajeRecibido, bufferMensajeRecibido.length);
            socketMulticast.receive(paquete);
            mensaje = new String(paquete.getData(), 0, paquete.getLength());

            manejarMensajesRecibidos(mensaje);
        }
    }

    /**
     * Este método se encarga de enviar los mensajes al grupo
     */
    private void runEnviar() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String mensaje;

        while (enEjecucion) {
            System.out.print("Mensaje a enviar: ");
            mensaje = bufferedReader.readLine();

            // Cuando introducimos el carácter "/", salimos de la función y nos desconectamos del cliente
            if (mensaje.trim().equals("/")) {
                enEjecucion = false;
                break;
            }

            // Añadimos el nombre del usuario que envió el mensaje al principio del mensaje:
            mensaje = "\n- " + nombre + ": " + mensaje;

            DatagramPacket paquete = new DatagramPacket(mensaje.getBytes(), mensaje.length(), grupo.getAddress(), puerto);
            socketMulticast.send(paquete);
        }
    }

    /**
     * Con este método, manejaremos los mensajes que recibimos en "runRecibir"
     */
    private void manejarMensajesRecibidos(String mensaje) throws IOException {
        // Obtenemos solamente el mensaje del usuario
        String[] partes = mensaje.split(": ", 2);
        String nombreUsuario = partes[0].substring(3);  //el substring lo usamos para quitar el "- " (guion y espacio), dejando solamente el nombre de usuario
        String mensajeRecibido = partes[1];

        if (mensajeRecibido.startsWith("Privado:")) {
            crearClienteTCPMensajePrivado(mensajeRecibido);
        }
        else if (mensajeRecibido.equalsIgnoreCase("Descargar")) {
            int x = new Random().nextInt(100) + 1;
            System.out.println("Se han descargado " + x + " archivos");
        }
        else {
            System.out.println(mensaje);
        }
    }

    private void crearClienteTCPMensajePrivado(String mensaje) throws IOException {
        String[] partes = mensaje.split(":", 3);
        String nombreUsuario = partes[1];
        String mensajeRecibido = partes[2];

        if (partes.length == 3) {
            ClienteStream cliente = new ClienteStream(new Socket(), puertoPrivado, "localhost", ipPrivada);
            cliente.main(null);
        }
    }
}
