import java.io.*;

public class ParentProcess {
    public static void main(String[] args) {
        try {
            // Launch the child process
            Process childProcess = new ProcessBuilder("java", "ChildProcess").start();

            // Set up input and output streams
            OutputStream outputStream = childProcess.getOutputStream();
            InputStream inputStream = childProcess.getInputStream();

            // Write bytes to the child process
            String message = "Hello, child process!";
            outputStream.write(message.getBytes());
            outputStream.flush();
            outputStream.close();

            // Read the response from the child process
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String response = reader.readLine();

            System.out.println("Response from child: " + response);

            // Wait for the child process to complete
            int exitCode = childProcess.waitFor();
            System.out.println("Child process exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ChildProcess {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();

            // Convert the input to uppercase
            String uppercaseInput = input.toUpperCase();

            // Write the uppercase result back to the parent
            System.out.println(uppercaseInput);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}