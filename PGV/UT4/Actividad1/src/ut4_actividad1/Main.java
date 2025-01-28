package ut4_actividad1;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class Main {
    final static String SITE = "ftp.nluug.nl";
    final static String USER = "anonymous";

    public static void main(String[] args) {
        FTPClient ftpclient = new FTPClient();
        try {
            ftpclient.connect(SITE);

            if (FTPReply.isPositiveCompletion(ftpclient.getReplyCode())) {
                System.out.println(ftpclient.getReplyString());
                if (ftpclient.login(USER, "")) {
                    ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpclient.enterLocalPassiveMode(); // Modo pasivo para evitar problemas con firewalls

                    // Crea directorio local si no existe
                    File downloadDir = new File("./archivos/");
                    if (!downloadDir.exists()) {
                        downloadDir.mkdirs();
                    }

                    // Cambiar a un directorio conocido
                    System.out.println("Directorio inicial: " + ftpclient.printWorkingDirectory());
                    ftpclient.changeWorkingDirectory("/pub/"); // Cambiar al directorio conocido
                    System.out.println("Directorio actual: " + ftpclient.printWorkingDirectory());

                    // Listar y descargar archivos
                    ListFiles(ftpclient);
                    Descarga(ftpclient);
                } else {
                    System.out.println("Usuario incorrecto");
                }
            } else {
                System.out.println("Error de acceso, desconectamos");
                ftpclient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error durante la conexión o transferencia: " + e.getMessage());
        } finally {
            try {
                if (ftpclient.isConnected()) {
                    ftpclient.logout();
                    ftpclient.disconnect();
                }
            } catch (IOException e) {
                System.err.println("Error al cerrar la conexión FTP: " + e.getMessage());
            }
        }
    }

    private static void Descarga(FTPClient ftpclient) throws IOException {
        BufferedOutputStream bo;
        FTPFile[] remotefiles = ftpclient.listFiles();

        for (FTPFile f : remotefiles) {
            if (f.isFile()) {
                // Asegurarse de que los nombres de archivos sean válidos en el sistema operativo local
                String safeFileName = f.getName().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
                bo = new BufferedOutputStream(new FileOutputStream("./archivos/" + safeFileName));
                if (ftpclient.retrieveFile(f.getName(), bo)) {
                    System.out.println("Descarga de " + f.getName() + " correcta");
                } else {
                    System.err.println("Descarga de " + f.getName() + " incorrecta");
                }
                bo.close();
            }
        }
    }

    private static void ListFiles(FTPClient ftpclient) throws IOException {
        FTPFile[] remotefiles = ftpclient.listFiles();
        if (remotefiles.length == 0) {
            System.out.println("No se encontraron ficheros/directorios en el directorio actual.");
        } else {
            System.out.println("Se han encontrado " + remotefiles.length + " ficheros/directorios:");
            for (FTPFile f : remotefiles) {
                if (f.isFile()) {
                    System.out.println("Archivo: " + f.getName());
                } else if (f.isDirectory()) {
                    System.out.println("Directorio: " + f.getName());
                }
            }
        }
    }
}
