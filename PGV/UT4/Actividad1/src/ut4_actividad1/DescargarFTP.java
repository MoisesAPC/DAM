package ut4_actividad1;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * Esta clase descargará los ficheros (y SOLO los ficheros)
 * del directorio "/pub/" del servidor FTP de ftp.nluug.nl
 */
public class DescargarFTP {
	//final static String SITE = "192.168.11.75"; //Con el filezilla en pc profe
	final static String SITE = "ftp.nluug.nl";
	final static String USER = "anonymous";
	final static String PASSW = "";

	final static String directorioLocalDescarga = "./archivos/";

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
					// Necesario para que los archivos se descarguen bien (de tipo binario) (ponerlo ANTES de descargar / subir archivos)
					ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
					ftpClient.enterLocalPassiveMode(); // Modo pasivo para evitar problemas con firewalls

					// Creamos un directorio local si no existe
					// En este directorio alojaremos los archivos descargados
					File downloadDir = new File(directorioLocalDescarga);
					if (!downloadDir.exists()) {
						downloadDir.mkdirs();
					}

					// Al principio, el working directory será el directorio raíz del servidor FTP ("/")...
					System.out.println("Directorio inicial: " + ftpClient.printWorkingDirectory());
					// ... por lo que ahora cambiaremos el working directory al que queramos (en este caso, "/pub/")
					ftpClient.changeWorkingDirectory("/pub/");
					System.out.println("Directorio actual: " + ftpClient.printWorkingDirectory());

					// Listamos los ficheros y descargarlos
					ListFiles(ftpClient);
					Descarga(ftpClient);
					// Descargamos ficheros y directorios / subdirectorios a partir
					// de la carpeta "/vol/2/jenkins/art/jenkins-logo/1024x1024" en el servidor
					// en nuestro directorio local "archivos_recursivo"
					DescargaRecursiva(ftpClient, "/vol/2/jenkins/art/jenkins-logo/1024x1024", "./archivos_recursivo/");
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
		finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			}
			catch (IOException e) {
				System.err.println("Error al cerrar la conexión FTP: " + e.getMessage());
			}
		}
	}
	
	// Para descargar SOLO ficheros en este ejemplo
	private static void Descarga(FTPClient ftpClient)throws IOException {
		BufferedOutputStream bufferedOutputStream;
		FTPFile[] remoteFiles = ftpClient.listFiles();

		for (FTPFile file : remoteFiles) {
			if (file.isFile()) {
				// Asegurarse de que los nombres de archivos sean válidos en el sistema operativo local
				String safeFileName = file.getName().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
				// Obtenemos el nombre del directorio + archivo donde sedescargarán los archivos
				bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(directorioLocalDescarga + safeFileName));

				try {
					// Necesario para que los archivos se descarguen bien (de tipo binario) (ponerlo ANTES de descargar / subir archivos)
					ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
					// Descargamos el fichero con el "retrieveFile()"
					if (ftpClient.retrieveFile(file.getName(), bufferedOutputStream)) {
						System.out.println("Descarga de " + file.getName() + " correcta");
					}
					else {
						System.err.println("Descarga de " + file.getName() + " incorrecta");
					}
				} finally {
					// Cerramos el BufferedOutputStream después de cada descarga
					// Necesario para que los ficheros descargados NO estén vacíos
					if (bufferedOutputStream != null) {
						bufferedOutputStream.close();
					}
				}
			}
		}
	}

	// Para obtener info de archivos y directorios
	private static void ListFiles(FTPClient ftpClient) throws IOException {
		String currentDir = ftpClient.printWorkingDirectory();
		FTPFile[] remoteFiles = ftpClient.listFiles();

		System.out.println("WORKING DIRECTORY: " + currentDir);

		System.out.println("Se han encontrado " + remoteFiles.length + " ficheros / directorios");
		for (FTPFile file : remoteFiles) {
			if (remoteFiles.length == 0) {
				System.out.println("No se encontraron ficheros/directorios en el directorio actual.");
			}
			else {
				//System.out.println(file);
				// O con métodos...
				//System.out.println(file.getName());
				if (file.isFile()) {
					System.out.println("Archivo: " + file.getName());
				}
				else if (file.isDirectory()) {
					System.out.println("Directorio: " + file.getName());
				}
			}
		}
	}

	/**
	 * Dado un directorio remoto raíz dado ("directorioRemoto"), este método descarga todos los ficheros y carpetas / subcarpetas
	 * que van desde ese directorio raíz en el directorio "directorioDescargaLocal"
	 */
	private static void DescargaRecursiva(FTPClient ftpClient, String directorioRemoto, String directorioDescargaLocal) throws IOException {
		FTPFile[] remoteFiles = ftpClient.listFiles(directorioRemoto);
		File localDirectory = new File(directorioDescargaLocal);
		if (!localDirectory.exists()) {
			localDirectory.mkdirs();
		}

		for (FTPFile file : remoteFiles) {
			String ficheroDirRemoto = directorioRemoto + "/" + file.getName();
			String ficheroDirLocal = directorioDescargaLocal + File.separator + file.getName();

			if (file.isDirectory()) {
				new File(ficheroDirLocal).mkdirs();
				DescargaRecursiva(ftpClient, ficheroDirRemoto, ficheroDirLocal);
			}
			else if (file.isFile()) {
				try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(ficheroDirLocal))) {
					if (ftpClient.retrieveFile(ficheroDirRemoto, bufferedOutputStream)) {
						System.out.println("Descarga correcta: " + ficheroDirRemoto);
					}
					else {
						System.err.println("Error al descargar: " + ficheroDirRemoto);
					}
				}
				catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
