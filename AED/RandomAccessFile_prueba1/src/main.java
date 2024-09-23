import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class main {
	public static void main(String[] args) {
		String line;
		String[] vecline;
		String sfile = "./informe2DAM.csv";
		
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
			
			for (Students st: Students.getList()) {
				System.out.println(st.toString());
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
