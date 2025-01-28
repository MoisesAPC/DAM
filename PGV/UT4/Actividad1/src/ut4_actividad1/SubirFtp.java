package ut4_actividad1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class SubirFtp {
	public static void main(String[] args) throws IOException {
		FTPClient cliente = new FTPClient();
		final String servidorFTP_IP = "localhost";	// 127.0.0.1 - Para cuando ejecutemos el FileZilla server en local
		final String usuario = "anonymous";			// Usuario con el que nos conectamos por defecto
		final String password = "";					// Contraseña con la que nos conectamos por defecto
		final String directorio = "/ftp";			// Working directory base en el servidor FTP

		try {
			// Nos conectamos
			cliente.connect(servidorFTP_IP);
			// Hacemos el login
			boolean login = cliente.login(usuario, password);

			if (login) {
				// Cambiamos el working directory base del servidor
				cliente.changeWorkingDirectory(directorio);
				// Especificamos el tipo del fichero que queremos descargar (en este caso, queremos descargar ficheros binarios)
				cliente.setFileType(FTP.BINARY_FILE_TYPE);

				// Stream para subir archivos
				BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("mensajeIRIS.txt"));
				// Subimos el fichero "mensajaeIRIS.txt"
				cliente.storeFile("mensajaeIRIS.txt", bufferedInputStream);
				bufferedInputStream.close();

				// Se desconecta el cliente
				cliente.logout();
			}
			else {
				System.out.println("Usuario incorrecto");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		cliente.disconnect();
	}
}
