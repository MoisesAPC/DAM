package Javier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class BatPrueba {

	public static void main(String[] args) throws IOException {
		
		String line;
		
		Process p_host=new ProcessBuilder("hostname").start();
		Process p_bat=new ProcessBuilder("cmd","/C","ejemplout1.bat").start();
		
		
		OutputStream os=p_bat.getOutputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(p_host.getInputStream()));
		
		line=br.readLine();
		
		os.write((line+"\n").getBytes());
		os.flush();
		
		br=new BufferedReader(new InputStreamReader(p_bat.getInputStream()));
		
		line="";
		while((line=br.readLine())!=null) {
			System.out.println(line);
		}
		
		os.close();

	}

}
