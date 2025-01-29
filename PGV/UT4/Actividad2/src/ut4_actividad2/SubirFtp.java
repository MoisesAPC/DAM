package ut4_actividad2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class SubirFtp {
	public static void main(String[] args) throws IOException {
		FTPClient ftpClient = new FTPClient();
		String servidorFTP = "localhost";
		String usuario = "anonymous";
		String password = "";
		String directorio = "/ftp";

		try {
			// Nos conectamos
			ftpClient.connect(servidorFTP);

			// Login
			boolean login = ftpClient.login(usuario, password);
			if (login) {
				ftpClient.changeWorkingDirectory(directorio);

				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				// Stream para subir archivos
				BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("mensajeIRIS.txt"));
				ftpClient.storeFile("mensajaeIRIS.txt", bufferedInputStream);
				bufferedInputStream.close();

				ftpClient.logout();
			}
			else {
				System.out.println("Usuario incorrecto");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		ftpClient.disconnect();
	}
}
