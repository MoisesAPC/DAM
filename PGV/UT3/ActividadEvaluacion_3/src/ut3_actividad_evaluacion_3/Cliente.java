package ut3_actividad_evaluacion_3;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Solo puede leer mensajes enviados por "UsuarioAdmin" dentro del grupo
 */
public class Cliente extends Thread {
    public static String direccionIPGrupo = "225.10.10.10";
    public static String direccionIPPrivada = "192.168.1.71";
    public static int puertoPublico = 6998;
    public static int puertoPrivado = 7668;
    public static MulticastSocket socketMulticast = null;
    public static InetSocketAddress grupo = null;
    public static NetworkInterface netIf = null;

    public static String nombre = "";

    private static boolean enEjecucion = true;

    public int tipoHilo = -1;
    public static final int enviar = 1;
    public static final int recibir = 2;
    public static final int privado = 3;

    public Cliente(int tipoHilo) {
        this.tipoHilo = tipoHilo;
    }

    private static void pedirNombre() {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduzca el nombre: ");
        nombre = teclado.nextLine();
    }

    public static void main(String[] args) {
        try {
            enEjecucion = true;
            pedirNombre();

            socketMulticast = new MulticastSocket(puertoPublico);
            InetAddress dir = InetAddress.getByName(direccionIPGrupo);
            grupo = new InetSocketAddress(dir, puertoPublico);
            netIf = NetworkInterface.getByInetAddress(dir);
            socketMulticast.joinGroup(grupo, netIf);

            Cliente hiloEnviar = new Cliente(enviar);
            Cliente hiloRecibir = new Cliente(recibir);
            Cliente hiloPrivado = new Cliente(privado);

            hiloEnviar.start();
            hiloRecibir.start();
            hiloPrivado.start();

            // Esperamos a que terminen los hilos antes de terminar el programa
            hiloEnviar.join();
            hiloRecibir.join();
            hiloPrivado.join();

            socketMulticast.leaveGroup(grupo, netIf);
            socketMulticast.close();
            System.out.println("Socket cerrado");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (SocketException e) {
            throw new RuntimeException(e);
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        switch (tipoHilo) {
            case enviar:
                runEnviar();
                //mensajePrivado();
                break;

            case recibir:
                runRecibir();
                break;

            case privado:
                mensajePrivado();
                break;
        }
    }

    /**
     * Recibe mensajes del grupo
     */
    private void runRecibir() {
        final int tamanoBufferMensaje = 1000;
        String mensaje;
        byte[] bufferMensajeRecibido = new byte[tamanoBufferMensaje];

        while (enEjecucion) {
            try {
                DatagramPacket paquete = new DatagramPacket(bufferMensajeRecibido, bufferMensajeRecibido.length);
                socketMulticast.receive(paquete);
                mensaje = new String(paquete.getData(), 0, paquete.getLength());
                System.out.println(mensaje);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Envía mensajes al grupo
     */
    private void runEnviar() {
        String mensaje;

        try {
            mensaje = "Se ha conectado " + nombre;
            DatagramPacket paquete = new DatagramPacket(mensaje.getBytes(), mensaje.length(), grupo.getAddress(), puertoPublico);
            socketMulticast.send(paquete);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Esta función permite el recibimiento de mensajes privados enviados por "Admin"
     */
    private void mensajePrivado() {
        final int tamanoBufferMensaje = 1000;

        try {
            /**
             * Enviamos los datos del cliente al administrador una sola vez cuando se conecte al grupo
             * para que el Admin pueda obtener los datos necesarios para realizar conexiones privadas.
             */
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress dirPrivado = InetAddress.getByName(direccionIPPrivada);

            String mensaje = new String("Nombre: " + nombre);
            DatagramPacket datagramaNombre = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, dirPrivado, puertoPrivado);
            datagramSocket.send(datagramaNombre);

            while (true) {
                byte[] respuesta = new byte[tamanoBufferMensaje];
                DatagramPacket datagramaMensajePrivado = new DatagramPacket(respuesta, respuesta.length);
                datagramSocket.receive(datagramaMensajePrivado);

                String respuestaString = new String(respuesta,0, datagramaMensajePrivado.getLength());
                System.out.println("Mensaje privado: " + respuestaString);
                final String mensajeConfirmacion = "Ok recibido mensaje privado";
                DatagramPacket datagramaMensajeConfirmacion = new DatagramPacket(mensajeConfirmacion.getBytes(), mensajeConfirmacion.getBytes().length, dirPrivado, puertoPrivado);
                datagramSocket.send(datagramaMensajeConfirmacion);

                // "FIN" es la palabra clave con la que el cliente se cerrará
                if (respuestaString.equalsIgnoreCase("FIN_CLIENTE")) {
                    break;
                }
            }
            datagramSocket.close();
            System.out.println("Cliente terminado");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

