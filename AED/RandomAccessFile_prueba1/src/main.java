import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class main {
	public static void main(String[] args) {
		String line;
		String[] vecline;
		String sfile = "./informe2DAM.csv";		// Fichero de entrada
		String dfile = "./informe2DAM.dat";		// Fichero de salida
		
		final int regSize = 72;		// Tamaño del registro
		final int nameSize = 60;	// Tamaño máximo del nombre, en bytes
		
		try {
			FileReader fr = new FileReader(sfile);
			
			// Leemos línea a línea
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				// El CSV está separado por punto y coma
				vecline = line.split(";");
				
				int numlist = Integer.parseInt(vecline[1]);
				String name = vecline[2];
				String surname = vecline[3];
				
				new Students(numlist, vecline[2] + " " + vecline[3] + " ", 0.0);
			}
			
			/*
			// Imprimimos el contenido de los objetos
			for (Students st: Students.getList()) {
				System.out.println(st.toString());
			}
			*/
			
			int sizelist =Students.getList().size();	// Número de estudiantes que tenemos
			
			/********** RandomAccessFile **********/
			
			RandomAccessFile raf = new RandomAccessFile(dfile, "rw");	// Abierto en modo lectura y escritura (rw)
			ArrayList<Students> myList = Students.getList();			// Lista de estudiantes
			
			for (int i = 0; i < sizelist; i++) {
				// Escribimos el número de la lista
				raf.write(myList.get(i).getNumlist());
				
				// Escribimos el nombre
				StringBuffer sb = new StringBuffer(myList.get(i).getName());
				sb.setLength(nameSize);
				raf.writeChars(sb.toString());
				
				// Escribimos las notas (marks)
				raf.writeDouble(myList.get(i).getMarks());
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
