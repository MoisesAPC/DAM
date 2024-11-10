package ModeloPCNo;

public class Lanzador {

	public static void main(String[] args) {
		
		int nump=3, numc=10;
		
		Buffer buffer= new Buffer();
		
		for(int i=0; i<nump; i++){
		
			Productor p=new Productor(buffer,i);
			p.start();
			
		}
		
		for(int i=0; i<numc; i++){
			
			Consumidor c=new Consumidor(buffer,i);
			c.start();
		}
		
		

	}

}
