en java, crea un programa que ejecute un proceso que escriba un fichero .bat a un directorio especifico, y que despues cree otro proceso que lance dicho .bat (sin usar cmd). ambos procesos deben de ser concurrentes (es decir, deben de empezar a la vez)

import java.io.IOException;

public class MainProcess {
    private static final String DIRECTORY = "C:\\temp";
    private static final String BAT_FILE = "script.bat";

    public static void main(String[] args) {
        try {
            // Iniciar el proceso de escritura
            ProcessBuilder writerPB = new ProcessBuilder("java", "WriterProcess", DIRECTORY, BAT_FILE);
            Process writerProcess = writerPB.start();

            // Iniciar el proceso de ejecución
            ProcessBuilder executorPB = new ProcessBuilder("java", "ExecutorProcess", DIRECTORY, BAT_FILE);
            Process executorProcess = executorPB.start();

            // Esperar a que ambos procesos terminen
            int writerExitCode = writerProcess.waitFor();
            int executorExitCode = executorProcess.waitFor();

            System.out.println("Proceso de escritura terminó con código: " + writerExitCode);
            System.out.println("Proceso de ejecución terminó con código: " + executorExitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class WriterProcess {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Se requieren argumentos: directorio y nombre del archivo");
            System.exit(1);
        }

        String directory = args[0];
        String fileName = args[1];

        try (FileWriter writer = new FileWriter(Paths.get(directory, fileName).toFile())) {
            writer.write("@echo off\n");
            writer.write("echo Hola desde el archivo batch!\n");
            writer.write("pause\n");
            System.out.println("Archivo batch creado: " + Paths.get(directory, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ExecutorProcess {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Se requieren argumentos: directorio y nombre del archivo");
            System.exit(1);
        }

        String directory = args[0];
        String fileName = args[1];
        File batFile = Paths.get(directory, fileName).toFile();

        // Esperar a que el archivo exista
        while (!batFile.exists()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(batFile.getAbsolutePath());
            Process p = pb.start();
            int exitCode = p.waitFor();
            System.out.println("Archivo batch ejecutado con código de salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

leido desde un .jar

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class MainProcess {
    private static final String DIRECTORY = "C:\\temp";
    private static final String BAT_FILE = "script.bat";
    private static final String EXECUTOR_JAR = "ExecutorProcess.jar";

    public static void main(String[] args) {
        try {
            // Iniciar el proceso de escritura
            ProcessBuilder writerPB = new ProcessBuilder("java", "WriterProcess", DIRECTORY, BAT_FILE);
            Process writerProcess = writerPB.start();

            // Iniciar el proceso de ejecución desde el JAR
            File jarFile = new File(EXECUTOR_JAR);
            if (!jarFile.exists()) {
                System.out.println("El archivo JAR del ejecutor no existe: " + EXECUTOR_JAR);
                System.exit(1);
            }

            ProcessBuilder executorPB = new ProcessBuilder(
                "java", 
                "-jar", 
                jarFile.getAbsolutePath(), 
                DIRECTORY, 
                BAT_FILE
            );
            Process executorProcess = executorPB.start();

            // Esperar a que ambos procesos terminen
            int writerExitCode = writerProcess.waitFor();
            int executorExitCode = executorProcess.waitFor();

            System.out.println("Proceso de escritura terminó con código: " + writerExitCode);
            System.out.println("Proceso de ejecución terminó con código: " + executorExitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

javac ExecutorProcess.java
jar cvfe ExecutorProcess.jar ExecutorProcess ExecutorProcess.class



con outputstream e inputstream

import java.io.*;

public class MainProcess {
    private static final String DIRECTORY = "C:\\temp";
    private static final String BAT_FILE = "script.bat";
    private static final String EXECUTOR_JAR = "ExecutorProcess.jar";

    public static void main(String[] args) {
        try {
            // Iniciar el proceso de escritura
            ProcessBuilder writerPB = new ProcessBuilder("java", "WriterProcess", DIRECTORY, BAT_FILE);
            Process writerProcess = writerPB.start();

            // Capturar la salida del proceso de escritura
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(writerProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Writer: " + line);
                }
            }

            // Iniciar el proceso de ejecución desde el JAR
            File jarFile = new File(EXECUTOR_JAR);
            if (!jarFile.exists()) {
                System.out.println("El archivo JAR del ejecutor no existe: " + EXECUTOR_JAR);
                System.exit(1);
            }

            ProcessBuilder executorPB = new ProcessBuilder(
                "java", 
                "-jar", 
                jarFile.getAbsolutePath(), 
                DIRECTORY, 
                BAT_FILE
            );
            Process executorProcess = executorPB.start();

            // Capturar la salida del proceso de ejecución
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(executorProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Executor: " + line);
                }
            }

            // Esperar a que ambos procesos terminen
            int writerExitCode = writerProcess.waitFor();
            int executorExitCode = executorProcess.waitFor();

            System.out.println("Proceso de escritura terminó con código: " + writerExitCode);
            System.out.println("Proceso de ejecución terminó con código: " + executorExitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.nio.file.Paths;

public class WriterProcess {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Se requieren argumentos: directorio y nombre del archivo");
            System.exit(1);
        }

        String directory = args[0];
        String fileName = args[1];
        File batFile = Paths.get(directory, fileName).toFile();

        try (OutputStream os = new FileOutputStream(batFile);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
            writer.write("@echo off\n");
            writer.write("echo Hola desde el archivo batch!\n");
            writer.write("pause\n");
            System.out.println("Archivo batch creado: " + batFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

import java.io.*;
import java.nio.file.Paths;

public class ExecutorProcess {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Se requieren argumentos: directorio y nombre del archivo");
            System.exit(1);
        }

        String directory = args[0];
        String fileName = args[1];
        File batFile = Paths.get(directory, fileName).toFile();

        // Esperar a que el archivo exista
        while (!batFile.exists()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(batFile.getAbsolutePath());
            Process p = pb.start();

            // Leer la salida del proceso
            try (InputStream is = p.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Batch output: " + line);
                }
            }

            int exitCode = p.waitFor();
            System.out.println("Archivo batch ejecutado con código de salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

