import java.io.IOException;

public class MainProcess {
    public static void main(String[] args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "FileWriterProcess");
            Process process = processBuilder.start();
            
            int exitCode = process.waitFor();
            System.out.println("Child process exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriterProcess {
    public static void main(String[] args) {
        String fileName = "hello.txt";
        
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.println("hello");
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
