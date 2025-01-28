package ut4_actividad1;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class DescargarFTP {

	//final static String SITE = "192.168.11.75"; //Con el filezilla en pc profe
	final static String SITE = "ftp.nluug.nl";
	final static String USER = "anonymous";
	final static String PASSW = "";

	public static void main(String[] args) {
		// Intentamos conexión FTP con el usuario "USER"

		FTPClient ftpClient = new FTPClient();
		try {
			// Nos conectamos
			ftpClient.connect(SITE);

			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				System.out.println(ftpClient.getReplyString());

				// Hacemos el login con el usuario cliente
				if (ftpClient.login(USER, PASSW)) {
					// Listamos los ficheros y descargarlos
					ListFiles(ftpClient);
					Descarga(ftpClient);
				}
				else {
					System.out.println("Usuario incorrecto");
				}
			}
			else {
				System.out.println("Error de acceso, desconectamos");
				ftpClient.disconnect();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Para descargar SOLO ficheros en este ejemplo
	private static void Descarga(FTPClient ftpclient)throws IOException {
		BufferedOutputStream bufferedOutputStream;
		FTPFile[] remoteFiles = ftpclient.listFiles();

		for (FTPFile file : remoteFiles) {
			if (file.isFile()) {
				// Asegurarse de que los nombres de archivos sean válidos en el sistema operativo local
				String safeFileName = file.getName().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
				bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("./archivos/" + safeFileName));
				if (ftpclient.retrieveFile(file.getName(), bufferedOutputStream)) {
					System.out.println("Descarga de " + file.getName() + " correcta");
				}
				else {
					System.err.println("Descarga de " + file.getName() + " incorrecta");
				}
			}
		}
	}

	// Para obtener info de archivos y directorios
	private static void ListFiles(FTPClient ftpclient) throws IOException {

		String currentDir = ftpclient.printWorkingDirectory();

		FTPFile[] remotefiles = ftpclient.listFiles();

		System.out.println("Se han encontrado " + remotefiles.length + " ficheros/directorios");
		for (FTPFile f : remotefiles) {
			System.out.println(f);
			// O con métodos...
			// System.out.println(f.getName());
		}
	}

}
