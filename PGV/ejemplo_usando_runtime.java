// ParentProcess.java
import java.io.*;

public class ParentProcess {
    public static void main(String[] args) throws Exception {
        Process child = Runtime.getRuntime().exec("java ChildProcess");
        
        // OutputStream to write to child process
        OutputStream out = child.getOutputStream();
        out.write("Hello from parent\n".getBytes());
        out.flush();
        
        // InputStream to read from child process
        InputStream in = child.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        System.out.println("Child says: " + reader.readLine());
        
        out.close();
        in.close();
    }
}

// ChildProcess.java
import java.io.*;

public class ChildProcess {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String message = reader.readLine();
        System.out.println("Received: " + message);
        
        System.out.println("Hello from child");
    }
}