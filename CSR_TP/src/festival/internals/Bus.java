package festival.internals;

import java.util.ArrayList;
import java.util.List;

public class Bus extends Thread{	

	// Places restantes
	private int placesDispo = 0;
	
	// Nombre de festivalier qu'on peut accueillir dans un bus
	private int placesMaxi = 0;
	
	// Id du bus
	private int idBus;
	
	// Définit si le bus roule ou non
	private boolean isOnTheRoadAgain = false;
	
	// Les festivalier dans le bus
	private List<Festivalier> festivaliers;

/**
 * ---- Getters and Setters ----
 */
	
	public int getPlacesDispo() {
		return placesDispo;
	}

	public void setPlacesDispo(int placesDispo) {
		this.placesDispo = placesDispo;
	}

	public int getPlacesMaxi() {
		return placesMaxi;
	}

	public void setPlacesMaxi(int placesMaxi) {
		this.placesMaxi = placesMaxi;
	}

	public int getIdBus() {
		return idBus;
	}

	public void setIdBus(int idBus) {
		this.idBus = idBus;
	}

	public boolean isOnTheRoadAgain() {
		return isOnTheRoadAgain;
	}

	public void setOnTheRoadAgain(boolean isOnTheRoadAgain) {
		this.isOnTheRoadAgain = isOnTheRoadAgain;
	}

	public List<Festivalier> getFestivaliers() {
		return festivaliers;
	}

	public void setFestivaliers(List<Festivalier> festivaliers) {
		this.festivaliers = festivaliers;
	}

/**
 * ---- ---------------- ----
 */	

	/**
	 * Constructeur utilisé par simulation
	 * 
	 * @param placesMaxi
	 */
	public Bus(int idBus, int placesMaxi){
		this.idBus = idBus;
		this.placesMaxi = placesMaxi;
		this.placesDispo = placesMaxi;
		this.isOnTheRoadAgain = false;
		this.festivaliers = new ArrayList<Festivalier>();
	}

	/**
	 * Constructeur utilisé par database
	 * L'id est set après
	 * 
	 * @param placesMaxi
	 */
	public Bus(int placesMaxi) {
		this.idBus = 0;
		this.placesMaxi = placesMaxi;
		this.placesDispo = placesMaxi;
		this.isOnTheRoadAgain = false;
		this.festivaliers = new ArrayList<Festivalier>();
	}

	/**
	 * On vide le bus des festivaliers
	 */
	public synchronized void viderBus() {
		
		// Pour chaque festivalier présent dans le bus, on les met au state D
		for(Festivalier f : this.festivaliers) {
			f.getStatus().put('D', System.currentTimeMillis());
			f.setMonBus(null);
			System.out.println("STATE D - Le festivalier " + f.getNumFestivalier() + " sort du bus n°" + this.idBus + " (" + this.placesDispo + " / " + this.placesMaxi + ").");
		}
		// On supprime tous les festivaliers du bus
		this.festivaliers.clear();

		// On remets toutes les places disponibles
		this.placesDispo = this.placesMaxi;
		
		// On notifie les festivaliers que le bus est disponible
		notifyAll();
	}

	
	/**
	 * Ajoute un festivalier dans le bus
	 * @param f
	 */
	public synchronized void prendrePassager(Festivalier f) {
		
		// Vérifie si le bus est disponible pour un passager de plus
		// Sinon le met en attente
		while (this.placesDispo == 0 && this.isOnTheRoadAgain){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
		
		this.placesDispo--;
		
		// Ajoute le festivalier au bus
		this.festivaliers.add(f);
	}
	
	/**
	 * Simule un temps pour atteindre le site d'arrivée
	 */
	public void roule(){
		
		// Indique que le bus n'est pas disponible
		this.isOnTheRoadAgain = true;
		
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Met le bus en attente
	 */
	public void waitFestivalier(){
		
		// Indique que le bus est disponible
		this.isOnTheRoadAgain = false;
	
		System.out.println("Le bus " + this.idBus + " attends les passagers.");

		Long heureActuelle = System.currentTimeMillis();
		Long heureDepart = System.currentTimeMillis()+5000;
	
		// Attends pendant 5s sans mettre en sleep
		while (heureActuelle <= heureDepart){
			heureActuelle = System.currentTimeMillis();
		}
		
		System.out.println("Le bus " + this.idBus + " repars.");
	}


	public void run(){

		// Les bus tourne en permanence
		while(true){
			
			System.out.println("Le bus : " + this.getIdBus() + " est disponible !");			
			
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
