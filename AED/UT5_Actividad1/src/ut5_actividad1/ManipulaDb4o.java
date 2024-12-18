package ut5_actividad1;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class ManipulaDb4o {

	public static void main(String[] args) {
			// Abre la BD departamentos, si no existe, la crea
			ObjectContainer db = Db4oEmbedded.openFile(
					Db4oEmbedded.newConfiguration(), "connections/bdpersonas.yap");
			
			//ObjectSet<Personas> p=db.queryByExample(new Personas(0,null,null,null));
			ObjectSet<Personas> p=db.queryByExample(new Personas());
			System.out.println("n� de personas: "+ p.size());
			
			while (p.hasNext()) {
				Personas per=p.next();
				System.out.println("Nombre : "+per.getNombre()+" "+"Direcci�n :" +per.getDomicilio());
			}
			
			//Consulta de las personas que viven en Telde
			p=db.queryByExample(new Personas(0,null,null,"Telde"));
			System.out.println("n� de personas que viven en Telde: "+ p.size());
			
			while (p.hasNext()) {
				Personas per=p.next();
				System.out.println("Nombre : "+per.getNombre()+" "+"Direcci�n :" +per.getDomicilio());
			}
			
			//Se modifica el domicilio de Luisa a Teror
			p=db.queryByExample(new Personas(0,null,"Luisa",null));
			if (p.size()==0) System.out.println("Luisa no existe");
			else{
				Personas per=p.next();
				per.setDomicilio("Teror");	//se modifica
				db.store(per);		//se vuelve a guardar
			}
			
			
			//Para eliminar al nif 43333333
			p=db.queryByExample(new Personas(0,"43333333",null,null));
			if (p.size()==0) System.out.println("Ese registro no exite");
			else{
				Personas per=p.next();
				db.delete(per);		//se borra
			}
			
			
			
			db.close();
	}

}
