package ut3_actividad_evaluacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

/**
 * Esta clase se corresponde con el cliente. El cliente se conecta a un grupo
 * junto con otros clientes
 */
public class Chat extends Thread {
    // Dirección IP del GRUPO. Todos los clientes se conectan a este grupo
    public static String direccionIPGrupo = "225.10.10.10";
    public static int puerto = 6998;
    public static MulticastSocket socketMulticast = null;
    public static InetSocketAddress grupo = null;
    public static NetworkInterface netIf = null;

    // Con esta variable nos aseguramos de que solamente un usuario pueda enviar mensajes
    // Para ello, la hacemos static. Lo ponemos a true de manera que solamente el primer usuario la pueda
    // poner a false
    private static Boolean puedeEnviarMensajes = true;

    public static String nombre = "";

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
        Chat hiloEnviar = null;
        Chat hiloRecibir = null;
System.out.println("ANTES: " + puedeEnviarMensajes);
        // Nos aseguramos de que solamente un usuario (el primero que acceda a esta parte del código)
        // pueda enviar mensajes (gracias a que "puedeEnviarMensajes" es static)
        if (puedeEnviarMensajes) {
            hiloEnviar = new Chat(HILO_TIPO_ENVIAR);
            hiloEnviar.start();

            puedeEnviarMensajes = false;
        }
System.out.println("DESPUES: " + puedeEnviarMensajes);
        hiloRecibir = new Chat(HILO_TIPO_RECIBIR);
        hiloRecibir.start();

        // Esperamos a que los hilos terminen
        try {
            if (hiloEnviar != null) {
                hiloEnviar.join();
            }
            if (hiloRecibir != null) {
                hiloRecibir.join();
            }

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
            System.out.println(mensaje);
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
}
