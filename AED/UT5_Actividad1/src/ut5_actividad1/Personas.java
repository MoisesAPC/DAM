package ut5_actividad1;

import com.db4o.ObjectSet;

public class Personas {
		private int cod;
		private String nif;
		private String nombre;
		private String domicilio;
	
		public Personas(){}
		
		public Personas(int cod ) {
			this.cod = cod;
		}

		public Personas(int cod, String nif,
				String nombre, String domicilio) {
			this.cod = cod;
			this.nif=nif;
			this.nombre = nombre;
			this.domicilio = domicilio;
		}

		public int getCod() {
			return cod;
		}

		public void setCod(int cod) {
			this.cod = cod;
		}

		public String getNif() {
			return nif;
		}

		public void setNif(String nif) {
			this.nif = nif;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getDomicilio() {
			return domicilio;
		}

		public void setDomicilio(String domicilio) {
			this.domicilio = domicilio;
		}
		
		
		
		
	}

				
		