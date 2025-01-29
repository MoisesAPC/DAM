package ut4_actividad2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class SubirFtp {
	final static String SITE = "localhost";	// También se pueden poner la IP
	final static String USER = "anonymous";
	final static String PASSW = "";
	final static String directorioEnRemoto = "/";	// Directorio en remoto donde se alojarán los archivos

	public static void main(String[] args) throws IOException {
		FTPClient ftpClient = new FTPClient();

		try {
			// Nos conectamos
			ftpClient.connect(SITE);

			if (ftpClient.login(USER, PASSW)) {
				// Necesario para que los archivos se descarguen bien (de tipo binario) (ponerlo ANTES de descargar / subir archivos)
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.changeWorkingDirectory(directorioEnRemoto);

				System.out.println("-- ANTES --");
				ListFiles(ftpClient);

				Subir(ftpClient, "./archivos/prueba.txt", "ftp/hola");
				SubirRecursivo(ftpClient, new File("./archivos/"), directorioEnRemoto);

				System.out.println("-- DESPUÉS --");
				ListFiles(ftpClient);

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

	/**
	 * Sube un único fichero (guardado en "ficheroLocal") al servidor, en la ruta remote "directorioRemoto"
	 */
	private static void Subir(FTPClient ftpClient, String ficheroLocal, String directorioRemoto) throws IOException {
		File localFile = new File(ficheroLocal);
		String nombreFicheroRemoto = localFile.getName();
		String rutaCompletaRemota = directorioRemoto + "/" + nombreFicheroRemoto;

		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(localFile));

			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

			//nos aseguramos de que el directorio existe en remoto
			if (!ftpClient.changeWorkingDirectory(directorioRemoto)) {
				if (ftpClient.makeDirectory(directorioRemoto)) {
					ftpClient.changeWorkingDirectory(directorioRemoto);
				}
				else {
					System.out.println("No se pudo crear el directorio remoto: " + directorioRemoto);
					return;
				}
			}

			if (ftpClient.storeFile(nombreFicheroRemoto, bufferedInputStream)) {
				System.out.println("Fichero subido con exito: " + ficheroLocal + " -> " + rutaCompletaRemota);
			}
			else {
				System.out.println("No se pudo subir el archivo: " + ficheroLocal);
				System.out.println("Server reply: " + ftpClient.getReplyString());
			}
		}
		catch (IOException e) {
			System.out.println("Error al subir el archivo: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private static void SubirRecursivo(FTPClient ftpClient, File directorioLocal, String directorioRemoto) throws IOException {
		if (directorioLocal.isDirectory()) {
			String subdirectorioRemoto = directorioRemoto + "/" + directorioLocal.getName();

			ftpClient.makeDirectory(subdirectorioRemoto);
			for (File file : directorioLocal.listFiles()) {
				SubirRecursivo(ftpClient, file, subdirectorioRemoto);
			}
		}
		else {
			try {
				BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(directorioLocal));

				ftpClient.storeFile(directorioRemoto + "/" + directorioLocal.getName(), bufferedInputStream);
				System.out.println("Subido: " + directorioLocal.getPath() + " -> " + directorioRemoto + "/" + directorioLocal.getName());
			}
			catch (IOException e) {
                throw new RuntimeException(e);
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
}
