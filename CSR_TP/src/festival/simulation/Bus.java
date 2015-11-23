package festival.simulation;

import java.security.Timestamp;

public class Bus extends Thread{	

	public int placesDispo = 0;
	public int placesMaxi = 0;
	public int idBus;
	public boolean isOnTheRoadAgain = true;


	public Bus(){
		this.setDaemon(true);
	}

	public Bus(int idBus, int placesMaxi){
		this.idBus = idBus;
		this.placesMaxi = placesMaxi;
		this.placesDispo = placesMaxi;
		this.isOnTheRoadAgain = false;
		this.setDaemon(true);
	}

	public synchronized void viderBus() {		


		this.placesDispo = this.placesMaxi;

		
	}

	public synchronized void prendrePassager() {
		while (this.placesDispo <= this.placesMaxi && this.placesDispo > 0 && !this.isOnTheRoadAgain){
			this.placesDispo--;
		}
	}
	
	public void roule(){
		this.isOnTheRoadAgain = true;
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void waitFestivalier(){
		this.isOnTheRoadAgain = false;
	}


	public void run(){

		// tant qu'il y a des threads actifs
		while(true){
			
			System.out.println("Le bus : " + this.idBus + " est disponible !");			
			
			Timestamp heureActuelle;
			Timestamp heureDepart;
			
			// Attends les passagers
			waitFestivalier();
			
			// Envoie les passagers
			roule();

			// Temps de retour du bus
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			

		}
	}

}
