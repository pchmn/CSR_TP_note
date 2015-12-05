package festival.internals;


public class Billeterie {

	/* Constantes associ�es a la billeterie */

	static final int stock = 100;

	private int currentStock;

	public int getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}


	public Billeterie() {
		this.currentStock = stock;
	}
	
	public Billeterie(int nouveauStock) {
		this.currentStock = nouveauStock;
	}


	/**
	 * Diminue le stock jusqu'a 0
	 */
	public synchronized void vendre() {

		// Met les threads festivalier en attente si il ne reste plus de places
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
