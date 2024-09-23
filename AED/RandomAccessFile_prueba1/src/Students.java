import java.util.ArrayList;

public class Students {
	@Override
	public String toString() {
		return "Students [numlist=" + numlist + ", name=" + name + ", marks=" + marks + "]";
	}

	private int numlist;	// Número de la lista
	private String name;	// Nombre y apellidos
	private double marks;	// Notas
	
	// Lista con todos los estudiantes añadidos
	static ArrayList<Students> list = new ArrayList<>();
	
	public static ArrayList<Students> getList() {
		return list;
	}
	
	public Students (int numlist, String name, double marks) {
		super();
		this.setNumlist(numlist);
		this.setName(name);
		this.setMarks(marks);
		
		// Añadimos el nuevo estudiante a la lista
		list.add(this);
	}

	int getNumlist() {
		return numlist;
	}

	void setNumlist(int numlist) {
		this.numlist = numlist;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	double getMarks() {
		return marks;
	}

	void setMarks(double marks) {
		this.marks = marks;
	}
}
