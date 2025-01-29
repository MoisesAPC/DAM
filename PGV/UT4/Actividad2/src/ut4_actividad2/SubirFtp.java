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
	final static String directorioEnRemoto = "/ftp";	// Directorio en remoto donde se alojarán los archivos

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

				Subir(ftpClient, "./archivos/prueba.txt");
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

	private static void Subir(FTPClient ftpClient, String ficheroLocal) throws IOException {
		// Stream para subir archivos
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(ficheroLocal));

		// Necesario para que los archivos se descarguen bien (de tipo binario) (ponerlo ANTES de descargar / subir archivos)
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.storeFile(ficheroLocal, bufferedInputStream);

		// Cerramos el BufferedInputStream después de cada descarga
		// Necesario para que los ficheros descargados NO estén vacíos
		if (bufferedInputStream != null) {
			bufferedInputStream.close();
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
