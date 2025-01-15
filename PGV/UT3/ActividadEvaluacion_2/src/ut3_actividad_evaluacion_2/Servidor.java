package ut3_actividad_evaluacion_2;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class Servidor implements Runnable {
    private static final int puertoPrivado = 7668;
    private static final int puertoPublico = 6998;
    private static final ArrayList<Cliente> clientesConectados = new ArrayList<>();
    private final String direccion =  "225.10.10.10";
    private Cliente admin = null;

    public static void main(String[] args) {
        Thread servidor = new Thread(new Servidor());
        servidor.start();
    }

    @Override
    public void run() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(puertoPrivado);
            MulticastSocket multicastSocket = new MulticastSocket(puertoPublico);
            InetAddress servidor = InetAddress.getByName(direccion);
            multicastSocket.joinGroup(servidor);
            while (true) {
                procesarPaquetes(datagramSocket, multicastSocket, servidor);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void procesarPaquetes(DatagramSocket datagramSocket, MulticastSocket multicastSocket, InetAddress servidor) {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(paqueteRecibido);
            String[] mensaje  = (new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength()).trim()).split(":");
            String emisor = mensaje[0];
            String contenido = mensaje[1];
            int puertoMensaje = paqueteRecibido.getPort();
            InetAddress direccionMensaje = paqueteRecibido.getAddress();
            if (contenido.equals("/clientenuevo")) {
                Cliente clienteNuevo = new Cliente(emisor, puertoMensaje);
                clientesConectados.add(clienteNuevo);
            }
            else if (contenido.equals("/descargar")) {
                int numArchivos = new Random().nextInt(100) + 1;
                String respuesta = "Se han descargado " + numArchivos + " archivos";
                datagramSocket.send(respuestaServidor(puertoMensaje, direccionMensaje, respuesta));
            }
            else if (admin == null) {
                if (contenido.equals("/admin")) {
                    datagramSocket.send(activarAdmin(emisor, puertoMensaje, direccionMensaje));
                }
                else {
                    String respuesta = "Ya hay un administrador activo";
                    datagramSocket.send(respuestaServidor(puertoMensaje, direccionMensaje, respuesta));
                }
            }
            else {
                if (contenido.equals("/admin")) {
                    String respuesta = "Ya hay un administrador activo";
                    datagramSocket.send(respuestaServidor(puertoMensaje, direccionMensaje, respuesta));
                }
                else {
                    if (emisor.equals(admin.getNombre())) {
                        if (contenido.equals("/privado")) {
                            if (mensaje.length == 4) {
                                String destinatario = mensaje[2];
                                String contenidoMensaje = "- Admin (Privado)" + admin.getNombre() + ": " + mensaje[3];
                                DatagramPacket paqueteEnvio = enviarMensajePrivado(destinatario, contenidoMensaje);
                                if (paqueteEnvio != null) {
                                    datagramSocket.send(paqueteEnvio);
                                }
                                else {
                                    String respuesta = "Error de comando";
                                    datagramSocket.send(respuestaServidor(puertoMensaje, direccionMensaje, respuesta));
                                }
                            }
                        }
                        else if (contenido.equals("/salir")) {
                            admin = null;
                            String respuesta = "Ya no eres admin";
                            datagramSocket.send(respuestaServidor(puertoMensaje, direccionMensaje, respuesta));
                        }
                        else {
                            String transmision = "- " + admin.getNombre() + " (mensaje grupal): " + contenido + ":" + admin.getPuerto();
                            enviarMensajeGrupo(multicastSocket, servidor, transmision);
                        }
                    }
                    else {
                        String respuesta = "Debes ser el administrador para enviar mensajes";
                        datagramSocket.send(respuestaServidor(puertoMensaje, direccionMensaje, respuesta));
                    }
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DatagramPacket enviarMensajePrivado(String destinatario, String contenidoMensaje) {
        try {
            for (int i = 0; i < clientesConectados.size(); i++) {
                if (clientesConectados.get(i).getNombre().equals(destinatario)) {
                    DatagramPacket paquete = new DatagramPacket(contenidoMensaje.getBytes(), contenidoMensaje.getBytes().length, clientesConectados.get(i).getDireccionCliente(), clientesConectados.get(i).getPuerto());
                    return paquete;
                }
            }

            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void enviarMensajeGrupo(MulticastSocket multicastSocket, InetAddress servidor, String mensaje) {
        try {
            DatagramPacket envio = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, servidor, puertoPublico);
            multicastSocket.send(envio);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DatagramPacket activarAdmin(String emisor, int puertoMensaje, InetAddress direccionMensaje) throws UnknownHostException {
        Cliente nuevoAdmin = new Cliente(emisor, puertoMensaje);
        admin = nuevoAdmin;
        String mensaje = "Ahora eres el admin:" + puertoMensaje + ":" + direccionMensaje;
        return respuestaServidor(puertoMensaje, direccionMensaje, mensaje);
    }

    private DatagramPacket respuestaServidor(int puertoMensaje, InetAddress direccionMensaje, String mensaje) {
        DatagramPacket paquete = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, direccionMensaje, puertoMensaje);
        return paquete;
    }
}
