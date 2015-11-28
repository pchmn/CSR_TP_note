package festival.simulation;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Bus extends Thread{	

	public int placesDispo = 0;
	public int placesMaxi = 0;
	public int idBus;
	public boolean isOnTheRoadAgain = false;
	public List<Festivalier> festivaliers;

	public Bus(int idBus, int placesMaxi){
		this.idBus = idBus;
		this.placesMaxi = placesMaxi;
		this.placesDispo = placesMaxi;
		this.isOnTheRoadAgain = false;
		this.festivaliers = new ArrayList<Festivalier>();
	}

	public synchronized void viderBus() {
		this.placesDispo = this.placesMaxi;
		for(Festivalier f : festivaliers) {
			f.state.put('D', System.currentTimeMillis());
			f.setMonBus(null);
			System.out.println("STATE D - Le festivalier " + f.numFestivalier + " sort du bus n°" + this.idBus + " (" + this.placesDispo + " / " + this.placesMaxi + ").");
		}
		festivaliers.clear();
		notifyAll();
		System.out.println("Le bus " + this.idBus + " est vide !");
		System.out.println(festivaliers.size());
	}

	public synchronized void prendrePassager(Festivalier f) {
		while (this.placesDispo == 0 && this.isOnTheRoadAgain){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
		this.placesDispo--;
		this.festivaliers.add(f);
		notifyAll();
	}
	
	public void roule(){
		this.isOnTheRoadAgain = true;
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void waitFestivalier(){
		this.isOnTheRoadAgain = false;
		
		Long heureActuelle = System.currentTimeMillis();
		Long heureDepart = System.currentTimeMillis()+5000;
		
		System.out.println("Le bus " + this.idBus + " attends les passagers.");
		while (heureActuelle <= heureDepart){
			heureActuelle = System.currentTimeMillis();
		}
		System.out.println("Le bus " + this.idBus + " repars.");
	}


	public void run(){

		// tant qu'il y a des threads actifs
		while(true){
			
			System.out.println("Le bus : " + this.idBus + " est disponible !");			
			
			// Attends les passagers
			waitFestivalier();
			
			// Envoie les passagers
			roule();

			// Temps de retour du bus
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			viderBus();			
		}
	}

}
