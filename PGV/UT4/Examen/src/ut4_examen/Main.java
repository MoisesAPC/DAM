package ut4_examen;

import java.io.*;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Main {
	final static String SITE = "localhost";	// También se pueden poner la IP
	final static String USER = "pgv_moises";
	final static String PASSW = "1234";
	final static String directorioEnRemoto = "/";	// Directorio raíz en remoto donde se alojarán los archivos
	final static String directorioLocalDescarga = "./local_prueba/";

	public static void main(String[] args) throws IOException {
		FTPClient ftpClient = new FTPClient();

		try {
			// Nos conectamos
			ftpClient.connect(SITE);

			if (ftpClient.login(USER, PASSW)) {
				// Necesario para que los archivos se descarguen bien (de tipo binario) (debemos ponerlo ANTES de descargar / subir archivos)
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

				/**
				 * 1) Accede al punto inicial del servidor. Muestra el contenido
				 */
				System.out.println("--- 1) Accede al punto inicial del servidor. Muestra el contenido ---");
				// Especificamos que el directorio base sea la raíz en remoto (SITIOFTP en local)
				ftpClient.changeWorkingDirectory(directorioEnRemoto);
				// Listamos los ficheros
				ListarFicheros(ftpClient);

				/**
				 * 2) Baja a la carpeta examen y muestra el contenido
				 */
				System.out.println("--- 2) Baja a la carpeta examen y muestra el contenido ---");
				// Vamos a la carpeta "examen"
				ftpClient.changeWorkingDirectory("/examen/");
				// Listamos los ficheros
				ListarFicheros(ftpClient);

				/**
				 * 3) Descarga todo el contenido en algún directorio en Local.
				 */
				System.out.println("--- 3) Descarga todo el contenido en algún directorio en Local. ---");
				Apartado3(ftpClient);

				/**
				 * 5) Desde el directorio local, sube uno de los ficheros descargados, a la carpeta del SitioFTP. Lista el resultado.
				 */
				System.out.println("--- 5) Desde el directorio local, sube uno de los ficheros descargados, a la carpeta del SitioFTP. Lista el resultado. ---");

				// Nuevamente especificamos que el directorio base sea la raíz en remoto (SITIOFTP en local)
				ftpClient.changeWorkingDirectory(directorioEnRemoto);

				System.out.println("-- ANTES --");
				ListarFicheros(ftpClient);

				// Subimos el fichero "local_prueba/aa3.txt"
				Subir(ftpClient, directorioLocalDescarga + "aa3.txt", "");

				System.out.println("-- DESPUÉS --");
				ListarFicheros(ftpClient);




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

	public static void Apartado3(FTPClient ftpClient) throws IOException {
		// Vamos a la carpeta "examen"
		ftpClient.changeWorkingDirectory("/examen/");
		// Necesario para que los archivos se descarguen bien (de tipo binario) (ponerlo ANTES de descargar / subir archivos)
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		// Modo pasivo para evitar problemas con firewalls
		ftpClient.enterLocalPassiveMode();

		// Creamos un directorio local si no existe
		// En este directorio alojaremos los archivos descargados
		// El directorio lo llamamos "local_prueba", situado en la raíz del proyecto,
		// el cual está FUERA de nuestro punto de anclaje del servidor (SITIOFTP)
		File downloadDir = new File(directorioLocalDescarga);
		if (!downloadDir.exists()) {
			downloadDir.mkdirs();
		}

		Descargar(ftpClient);

		System.out.println("Descarga de ficheros de carpeta examen - CORRECTO");
	}

	private static void Descargar(FTPClient ftpClient) throws IOException {
		BufferedOutputStream bufferedOutputStream;
		FTPFile[] remoteFiles = ftpClient.listFiles();

		for (FTPFile file : remoteFiles) {
			if (file.isFile()) {
				// Nos aseguramos de que los nombres de archivos sean validos en el sistema operativo local
				String safeFileName = file.getName().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
				// Obtenemos el nombre del directorio + archivo donde se descargaron los archivos
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
				}
				finally {
					// Cerramos el BufferedOutputStream despues de cada descarga
					// Necesario para que los ficheros descargados NO estan vacios
					if (bufferedOutputStream != null) {
						bufferedOutputStream.close();
					}
				}
			}
		}
	}

	/**
	 * Sube un único fichero (guardado en "ficheroLocal") al servidor, en la ruta remote "directorioRemoto"
	 * Si queremos subir un fichero a la raíz, basta con pasar una String vacia a "directorioRemoto"
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

	// Listamos los ficheros en el working directory actual
	private static void ListarFicheros(FTPClient ftpClient) throws IOException {
		String currentDir = ftpClient.printWorkingDirectory();
		FTPFile[] remoteFiles = ftpClient.listFiles();

		System.out.println("WORKING DIRECTORY: " + currentDir);

		System.out.println("Se han encontrado " + remoteFiles.length + " ficheros / directorios");
		for (FTPFile file : remoteFiles) {
			if (remoteFiles.length == 0) {
				System.out.println("No se encontraron ficheros/directorios en el directorio actual.");
			}
			else {
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
