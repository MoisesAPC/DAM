package ModeloPCNo;

public class Productor extends Thread {
	
	
	Buffer buffer;
	
	public Productor(Buffer buffer, int i) {
		this.buffer=buffer;
		this.setName("Prod_"+i);
	}

	public void run() {
		
		for(int i=0; i<3; i++) {
			buffer.producir();
			try {
				sleep((int)Math.random()*6000);
			} catch (InterruptedException e) {
				
			}
		}
		
		
	}

}
