import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class main {
	final static int regSize = 72;  // Tamaño del registro
	final static int nameSize = 60;	// Tamaño máximo del nombre, en bytes
	
	public static void main(String[] args) {
		String line;
		String[] vecline;
		String sfile = "./informe2DAM.csv";		// Fichero de entrada (fichero de texto)
		String dfile = "./informe2DAM.dat";		// Fichero de salida (fichero binario)
		
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
			
			
			// Imprimimos el contenido de los objetos
			for (Students st: Students.getList()) {
				System.out.println(st.toString());
			}
			
			
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
			raf.close();
			
			// Volvemos a abrir el .dat (previo fichero de salida)
			// para parsear sus contenidos
			raf = new RandomAccessFile(dfile, "r");
			
			while (raf.getFilePointer() < raf.length()) {
				// Número de lista
				System.out.print(raf.readInt() + " -- ");
				// Nombre y apellidos
				System.out.print(readMyName(raf));
				// Notas
				System.out.println(raf.readDouble());
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Parsea el nombre y apellidos del fichero binario de entrada (raf)
	private static String readMyName(RandomAccessFile raf) {
		String val = "";
		
		try {
			for (int i = 0; i < nameSize; i++) {
					val += raf.readChar();
				}
			}
		catch (IOException e) {
			//e.printStackTrace();
			System.out.print("entrando en catch\n");
		}
		
		// Usamos trim() para cortar los caracteres blancos
		// de la String final
		// https://www.w3schools.com/java/ref_string_trim.asp
		return val.trim();
	}

}
