package ut4_examen;
// Generated 29 ene 2025, 8:54:11 by Hibernate Tools 6.5.1.Final

/**
 * Alumnos generated by hbm2java
 */
public class Alumnos implements java.io.Serializable {

	private String nif;
	private String nombre;
	private String cial;
	private String telefono;

	public Alumnos() {
	}

	public Alumnos(String nif) {
		this.nif = nif;
	}

	public Alumnos(String nif, String nombre, String cial, String telefono) {
		this.nif = nif;
		this.nombre = nombre;
		this.cial = cial;
		this.telefono = telefono;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCial() {
		return this.cial;
	}

	public void setCial(String cial) {
		this.cial = cial;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
