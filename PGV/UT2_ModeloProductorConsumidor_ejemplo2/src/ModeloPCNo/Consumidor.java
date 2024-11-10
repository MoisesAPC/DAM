package ModeloPCNo;

public class Consumidor extends Thread {

	Buffer buffer;

	public Consumidor(Buffer buffer, int i) {
		this.buffer = buffer;
		this.setName("Cons_" + i);

	}

	public void run() {
		try {
			
			sleep((int)Math.random()*1000);
			for (int i=0; i<2; i++) {
		
				buffer.consumir();
			}
			sleep((int)Math.random()*1500);
		} catch (InterruptedException e) {
			
		}
		}
		
	}


