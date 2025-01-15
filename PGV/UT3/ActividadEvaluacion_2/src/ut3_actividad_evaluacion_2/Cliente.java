package ut3_actividad_evaluacion_2;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    private final static int puertoPrivado = 7668;
    private final static int puertoPublico = 6998;
    private final static String hostLocal = "localhost";
    private final static String direccion = "225.10.10.10";
    private String nombre;
    private InetAddress direccionCliente;
    private int puerto;

    public Cliente(String nombre, int puerto) throws UnknownHostException {
        this.nombre = nombre;
        this.puerto = puerto;
        this.direccionCliente = InetAddress.getByName(hostLocal);
    }

    public static void main(String[] args) {
        try {
            ejecutarCliente();
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ejecutarCliente() throws UnknownHostException {
        Cliente cliente = new Cliente(eligeNombre(), 0);

        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            MulticastSocket multicastSocket = new MulticastSocket(puertoPublico);
            InetAddress servidorPublico = InetAddress.getByName(direccion);
            InetAddress servidorLocal = InetAddress.getByName(hostLocal);
            multicastSocket.joinGroup(servidorPublico);
            conectarCliente(cliente.getNombre(), datagramSocket, servidorLocal);
            Thread chatPrivado = new Thread(() -> {
                escucharPrivado(datagramSocket, cliente);
            });
            Thread chatPublico = new Thread(() -> {
                escucharPublico(multicastSocket, cliente);
            });
            chatPrivado.start();
            chatPublico.start();
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String mensaje;
            while ((mensaje = consoleInput.readLine()) != null) {
                if (mensaje.isBlank()) {
                    System.err.println("Mensaje vacio");
                    continue;
                }
                mensaje = cliente.getNombre() + ":" + mensaje;
                enviarMensaje(mensaje, datagramSocket, servidorLocal);
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void enviarMensaje(String mensaje, DatagramSocket datagramSocket, InetAddress servidorLocal) throws IOException {
        DatagramPacket envio = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, servidorLocal, puertoPrivado);
        datagramSocket.send(envio);
    }

    private static void conectarCliente(String nombre, DatagramSocket datagramSocket, InetAddress servidorLocal) throws IOException {
        String mensaje = nombre + ":/clientenuevo";
        DatagramPacket envio = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, servidorLocal, puertoPrivado);
        datagramSocket.send(envio);
    }

    private static void escucharPrivado(DatagramSocket datagramSocket, Cliente cliente) {
        try {
            byte[] respuesta = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(respuesta, respuesta.length);
            datagramSocket.receive(paqueteRecibido);
            String[] mensaje = (new String(paqueteRecibido.getData()).trim()).split(":");
            if (mensaje[0].equals("Ahora eres el admin")) {
                cliente.setPuerto(Integer.parseInt(mensaje[1]));
            }
            System.out.println(mensaje[0]);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void escucharPublico(MulticastSocket multicastSocket, Cliente cliente) {
        try {
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(paqueteRecibido);
                String[] mensaje = (new String(paqueteRecibido.getData()).trim()).split(":");
                if (!mensaje[2].equals(String.valueOf(cliente.getPuerto()))) {
                    System.out.println(mensaje[0] + ": " + mensaje[1]);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String eligeNombre() {
        Scanner scanner = new Scanner(System.in);
        String nombre;

        while (true) {
            System.out.print("Introduce tu nombre: ");
            nombre = scanner.nextLine();
            if (nombre.isBlank()) {
                System.out.println("ERROR: Nombre invalido");
            }
            else {
                return nombre;
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public InetAddress getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(InetAddress direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
}
