package ut3_actividad_evaluacion_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Puede escribir al grupo
 */
public class Admin extends Thread {
    private static DatagramSocket datagramSocket = null;
    public static String direccionIPGrupo = "225.10.10.10";
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

    /**
     * Arrays en donde guardaremos los InetAddress, puertos y nombres de
     * los usuarios conectados al grupo (sin incluir al Admin)
     */
    private static ArrayList<InetAddress> arrayInetAddress = new ArrayList<>();
    private static ArrayList<Integer> arrayPuertos = new ArrayList<>();
    private static ArrayList<String> arrayNombres = new ArrayList<>();

    public Admin(int tipoHilo) {
        this.tipoHilo = tipoHilo;
    }

    private static void pedirNombre() {
        Scanner name = new Scanner(System.in);
        System.out.print("Escribe de nombre: ");
        nombre = name.nextLine();
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

            Admin hiloEnviar = new Admin(enviar);
            Admin hiloRecibir = new Admin(recibir);
            Admin hiloPrivado = new Admin(privado);

            hiloEnviar.start();
            hiloRecibir.start();
            hiloPrivado.start();

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
                break;

            case recibir:
                runRecibir();
                break;

            case privado:
                privadoDatagram();
                break;
        }
    }

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

    private void runEnviar() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String mensaje;

        while (enEjecucion) {
            try {
                // No podemos tener 9999 usuarios conectados, sino, daría error.
                System.out.println("Mensaje a enviar: ");
                mensaje = bufferedReader.readLine();
                String[] msg =  mensaje.split(":");
                String comando = msg[0];
                String nombre_usuario = null;
                String mensajeAEnviar = null;

                if (comando.equals("Privado")) {
                    if (msg.length == 3) {
                        nombre_usuario = msg[1];
                        mensajeAEnviar = msg[2];
                    }
                    else {
                        System.out.println("ERROR: El formato es Privado:nombre_usuario:mensaje");
                        continue;
                    }
                }

                // "FIN_ADMIN" es la palabra clave con la que el cliente se cerrará
                if (mensaje.trim().equalsIgnoreCase("FIN_ADMIN")) {
                    enEjecucion = false;
                    break;
                }

                if (comando.equals("Privado")) {
                    int x = obtenerIndiceDeCliente(nombre_usuario);

                    if (x == 9999) {
                        System.out.println("Persona no encontrada");
                    }
                    else {
                        String mensajeAEnviarString = mensajeAEnviar;
                        byte[] salida = mensajeAEnviarString.toString().getBytes();
                        DatagramPacket datagramaMensajeAEnviar = new DatagramPacket(salida, salida.length, arrayInetAddress.get(x), arrayPuertos.get(x));
                        datagramSocket.send(datagramaMensajeAEnviar);
                    }
                }
                else if (mensaje.equalsIgnoreCase("Descargar")) {
                    Random random = new Random();
                    int numArchivos = random.nextInt(100) + 1;
                    String mensajeDescarga="Se han descargado " + numArchivos + " archivos";

                    DatagramPacket datagramaMensajeDescarga = new DatagramPacket(mensajeDescarga.getBytes(), mensajeDescarga.length(), grupo.getAddress(), puertoPublico);
                    socketMulticast.send(datagramaMensajeDescarga);
                }
                else {
                    mensaje = "- " + nombre + ": " + mensaje;
                    DatagramPacket paquete = new DatagramPacket(mensaje.getBytes(), mensaje.length(), grupo.getAddress(), puertoPublico);
                    socketMulticast.send(paquete);
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Esta función permite el envío de mensajes privados
     */
    private void privadoDatagram() {
        try {
            datagramSocket = new DatagramSocket(puertoPrivado);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }

        while (datagramSocket != null) {
            final int tamanoBufferMensaje = 1000;

            try {
                byte[] entrada = new byte[tamanoBufferMensaje];
                DatagramPacket datagrama1 = new DatagramPacket(entrada, entrada.length);
                datagramSocket.receive(datagrama1);

                String mensaje = new String(datagrama1.getData(), 0, datagrama1.getLength());
                InetAddress dirCliente = datagrama1.getAddress();
                int puertoCliente = datagrama1.getPort();

                String[] comprobacion = mensaje.split(" ");
                String[] msg = mensaje.split(":");
                // El cliente solo puede enviar 2 mensajes por privado uno es el nombre y el otro confirmación de mensaje privado
                if (comprobacion[0].equals("Ok")) {
                    System.out.println(mensaje);
                }
                else {
                    // Añadimos al array para buscar el puerto de cada cliente
                    arrayInetAddress.add(dirCliente);
                    arrayPuertos.add(puertoCliente);
                    arrayNombres.add(msg[1]);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fin");
    }

    /**
     * Obtiene el índice del cliente en el array "arrayNombres" según su nombre
     */
    private static int obtenerIndiceDeCliente(String nombreUser) {
        for (int i = 0; i < arrayNombres.size(); i++) {
            if (arrayNombres.get(i).equals(nombreUser)) {
                return i;
            }
        }
        return 9999;
    }
}
