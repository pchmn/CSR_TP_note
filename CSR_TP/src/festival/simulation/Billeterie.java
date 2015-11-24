package festival.simulation;


public class Billeterie {

	/* Constantes associ�es a la billeterie */

	static final int stock = 100;

	public int currentStock;

	public Billeterie() {
		this.currentStock = stock;
	}


	public synchronized void vendre() {

		while(this.currentStock == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.currentStock--;

		notifyAll();
	}

}
