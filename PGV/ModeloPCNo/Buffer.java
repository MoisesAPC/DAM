package ModeloPCNo;

public class Buffer {
	
	static int contar=0;
	
	public synchronized void producir() {
		contar++;
		System.out.println("Soy el hilo "+Thread.currentThread().getName()+ " y contar vale "+contar);
	}
	
	public synchronized void consumir() {
		if (contar > 0) {
			contar--; 
			System.out.println("Soy el hilo "+Thread.currentThread().getName()+ " y contar vale "+contar);
		}else {
			System.out.println("EL hilo "+Thread.currentThread().getName()+ " no pudo consumir");
		}
	}
	
	

}
