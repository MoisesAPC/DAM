package prueba1;

import java.lang.String;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OBJ {
	private String nombre;
	private Date fecha;
	private String tipo;
	private final String pattern = "dd/MM/yyyy";
	
	public String getNombre() {
		return nombre;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setNombre(String nombre_) {
		nombre = nombre_;
	}
	
	public void setFecha(Date fecha_) {
		fecha = fecha_;
	}
	
	public void setFecha(String fecha_) {
		try {
            DateFormat df = new SimpleDateFormat(pattern);
            fecha = df.parse(fecha_);
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	public void setTipo(String tipo_) {
		tipo = tipo_;
	}
	
	public void print() {
		DateFormat df = new SimpleDateFormat(pattern);
		
		System.out.println(nombre);
		System.out.println(df.format(fecha));
		System.out.println(tipo);
	}
	
	// Transforma las variables a una sola línea separada por commas,
	// para luego ser escrita en un fichero
	public String formatear() {
		DateFormat df = new SimpleDateFormat(pattern);
		String fechaString = df.format(fecha);
		
		return nombre + "," + fechaString + "," + tipo;
	}
	
	public void parsearLineaDeFichero(String linea) {
		// elementosDeLinea[0] = nombre
		// elementosDeLinea[1] = fecha
		// elementosDeLinea[2] = tipo
		String[] elementosDeLinea = linea.split("[,]", 0);
		
		setNombre(elementosDeLinea[0]);
		setFecha(elementosDeLinea[1]);
		setTipo(elementosDeLinea[2]);
	}
	
	public OBJ(String linea) {
		parsearLineaDeFichero(linea);
	}
}
