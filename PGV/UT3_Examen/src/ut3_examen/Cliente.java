package ut3_examen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @note Al ejecutar el comando "Unir grupo", el teclado que envía mensajes se ejecuta ANTES
 * que el teclado que pide comandos nuevamente. Por este motivo, a veces es necesario introducir el mismo comando
 * dos veces en el teclado para que funcione.
 *
 * Ejemplo de salida del teclado en el cliente:
 *
 * Escribe el nombre: a
 * -- Comando: Unir grupo
 * -- Comando: Mensaje a enviar: Arranca cliente CHAT
 * a
 * -- Comando: a
 * Mensaje a enviar: - a: a
 * - b: b
 *
 * Fíjese que tuve que escribir el mensaje "a" dos veces para que el mensaje se enviase (la primera vez el programa cree que "a" es un comando)
 * Esto también se aplica al comando de salida "Exit", el cual finalizará cuando el usuario lo introduzca SI ESTA DENTRO de un grupo
 */
public class Cliente extends Thread {
    private static DatagramSocket datagramSocket = null;
    public static String direccionIPChat = "225.10.10.10";
    public static final String ipServidor = "192.168.56.1";
    public static int puertoChat = 8888;
    public static int puertoServidor = 25676;
    public static MulticastSocket socketMulticast = null;
    public static InetSocketAddress grupo = null;
    public static NetworkInterface netIf = null;

    public static String nombre = "";

    private static boolean enEjecucion = true;

    public int tipoHilo = -1;
    public static final int enviar = 1;
    public static final int recibir = 2;
    public static final int servidor = 3;
    public static final int ftp = 4;

    /**
     * Arrays en donde guardaremos los InetAddress, puertos y nombres de
     * los usuarios conectados al grupo (sin incluir al Admin)
     */
    private static ArrayList<InetAddress> arrayInetAddress = new ArrayList<>();
    private static ArrayList<Integer> arrayPuertos = new ArrayList<>();
    private static ArrayList<String> arrayNombres = new ArrayList<>();

    public Cliente(int tipoHilo) {
        this.tipoHilo = tipoHilo;
    }

    private static void pedirNombre() {
        Scanner name = new Scanner(System.in);
        System.out.print("Escribe el nombre: ");
        nombre = name.nextLine();
    }

    private static String pedirComando() {
        Scanner teclado = new Scanner(System.in);
        System.out.print("-- Comando: ");
        return teclado.nextLine();
    }

    public static void main(String[] args) {
        String comando = "";
        try {
            enEjecucion = true;
            pedirNombre();

            Cliente hiloEnviar = null;
            Cliente hiloRecibir = null;
            Cliente hiloServidor = null;
            Cliente hiloFTP = null;

            while (enEjecucion) {
                comando = pedirComando();

                // Comando "Unir grupo"
                if (comando.equalsIgnoreCase("Unir grupo")) {
                    hiloEnviar = new Cliente(enviar);
                    hiloRecibir = new Cliente(recibir);

                    socketMulticast = new MulticastSocket(puertoChat);
                    InetAddress dir = InetAddress.getByName(direccionIPChat);
                    grupo = new InetSocketAddress(dir, puertoChat);
                    netIf = NetworkInterface.getByInetAddress(dir);
                    socketMulticast.joinGroup(grupo, netIf);

                    hiloEnviar.start();
                    hiloRecibir.start();
                }
                else if (comando.equalsIgnoreCase("Enviar Where")) {
                    hiloServidor = new Cliente(servidor);

                    datagramSocket = new DatagramSocket();
                    InetAddress ipServidorInetAddress = InetAddress.getByName(ipServidor);
                    hiloServidor.start();
                    hiloServidor.join();
                }
                else if (comando.equalsIgnoreCase("Enviar FTP")) {
                    hiloFTP = new Cliente(ftp);

                    datagramSocket = new DatagramSocket();
                    InetAddress ipServidorInetAddress = InetAddress.getByName(ipServidor);
                    hiloFTP.start();
                    hiloFTP.join();
                }
                else if (comando.equalsIgnoreCase("Exit")) {
                    enEjecucion = false;
                    return;
                }
            }

            // Esperamos a que el usuario escriba "Exit" para detener el cliente
            hiloEnviar.join();
            hiloRecibir.join();

            if (socketMulticast != null) {
                socketMulticast.leaveGroup(grupo, netIf);
                socketMulticast.close();
            }

            System.out.println("Finaliza el cliente");
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
                runEnviarChat();
                break;

            case recibir:
                runRecibirChat();
                break;

            case servidor:
                runConexionServidor();
                break;

            case ftp:
                runConexionFTP();
                break;
        }
    }

    /**
     * Esta función recibe mensjaes del chat grupal
     */
    private void runRecibirChat() {
        System.out.println("Arranca cliente CHAT");

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
     * Esta función envía mensajes al chat grupal
     */
    private void runEnviarChat() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String mensaje;

        while (enEjecucion) {
            try {
                System.out.print("Mensaje a enviar: ");
                mensaje = bufferedReader.readLine();

                mensaje = "- " + nombre + ": " + mensaje;
                DatagramPacket paquete = new DatagramPacket(mensaje.getBytes(), mensaje.length(), grupo.getAddress(), puertoChat);
                socketMulticast.send(paquete);

                String[] msg = mensaje.split(":");
                String comandoExit = msg[1].trim();
                if (comandoExit.equalsIgnoreCase("Exit")) {
                    enEjecucion = false;
                    break;
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Esta función se conecta al servidor a través del Enviar Where
     * Luego, se espera hasta obtener la respuesta del servidor (su numero de puerto)
     * y finaliza el hilo
     */
    private void runConexionServidor() {
        try {
            // Esta es la IP del servidor (se puede averiguar, además, usando ipconfig
            InetAddress ipServidorInetAddress = InetAddress.getByName(ipServidor);

            final int tamanoBufferMensaje = 1000;
            final String mensajeConfirmacion = "Enviar Where";

            // Enviamos el paquete al servidor
            DatagramPacket paquete = new DatagramPacket(mensajeConfirmacion.getBytes(), mensajeConfirmacion.getBytes().length, ipServidorInetAddress, puertoServidor);
            datagramSocket.send(paquete);

            byte[] entrada = new byte[tamanoBufferMensaje];
            DatagramPacket datagramaReciboPaqueteInicioUsuario = new DatagramPacket(entrada, entrada.length);
            datagramSocket.receive(datagramaReciboPaqueteInicioUsuario);

            String mensaje = new String(datagramaReciboPaqueteInicioUsuario.getData(), 0, datagramaReciboPaqueteInicioUsuario.getLength());
            System.out.println(mensaje);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Esta función se conecta al servidor a través del Enviar Where
     * Luego, se espera hasta obtener la respuesta del servidor (numbero de archivos subidos)
     * y finaliza el hilo
     */
    private void runConexionFTP() {
        try {
            // Esta es la IP del servidor (se puede averiguar, además, usando ipconfig
            InetAddress ipServidorInetAddress = InetAddress.getByName(ipServidor);

                final int tamanoBufferMensaje = 1000;
                final String mensajeConfirmacion = "Enviar FTP";

                // Enviamos el paquete al servidor
                DatagramPacket paquete = new DatagramPacket(mensajeConfirmacion.getBytes(), mensajeConfirmacion.getBytes().length, ipServidorInetAddress, puertoServidor);
                datagramSocket.send(paquete);

                byte[] entrada = new byte[tamanoBufferMensaje];
                DatagramPacket datagramaReciboPaqueteInicioUsuario = new DatagramPacket(entrada, entrada.length);
                datagramSocket.receive(datagramaReciboPaqueteInicioUsuario);

                String mensaje = new String(datagramaReciboPaqueteInicioUsuario.getData(), 0, datagramaReciboPaqueteInicioUsuario.getLength());
                System.out.println(mensaje);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
