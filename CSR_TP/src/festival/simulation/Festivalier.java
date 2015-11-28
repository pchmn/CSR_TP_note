package festival.simulation;

import java.util.HashMap;


public class Festivalier extends Thread {

	public int numFestivalier;
	public HashMap<Character, Long> state = new HashMap<Character, Long>();
	public Bus monBus;
	public Billeterie maBilleterie;
	public SiteDepart siteDepart;

	public Festivalier(int numFestivalier, Billeterie billeterie, SiteDepart siteDepart) {
		this.maBilleterie = billeterie;
		this.numFestivalier = numFestivalier;
		this.siteDepart = siteDepart;
		this.state.put('A', System.currentTimeMillis());
		System.out.println("STATE A - Le festivalier " + this.numFestivalier + " est en route");
	}
	
	public void setMonBus(Bus bus){
		this.monBus = bus;
		if(bus != null) {
			System.out.println("Le festivalier " + this.numFestivalier + " a pris le bus " + this.monBus.idBus);	
		}
	}

	// Le festivalier achete un ticket
	public void acheter(){
		try {
			this.maBilleterie.vendre();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("STATE B - Le festivalier " + this.numFestivalier + " a achetÈ sa place");
		this.state.put('B', System.currentTimeMillis());
	}

	public void prendreBus(){
		
		// On v√©rifie qu'il poss√®de bien un ticket
		if (this.state.containsKey('B')) {
			
			this.siteDepart.monterBus(this);
			System.out.println("STATE C - Le festivalier " + this.numFestivalier + " monte dans le bus n∞" + this.monBus.idBus + " (" + this.monBus.placesDispo + " / " + this.monBus.placesMaxi + ").");
			this.state.put('C', System.currentTimeMillis());
		}
	}

	public synchronized void sortirBus(){
		
		// On v√©rifie qu'il √©tait bien dans le bus
		if (this.state.containsKey('C')) {
			while (!this.monBus.isOnTheRoadAgain){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("STATE D - Le festivalier " + this.numFestivalier + " sort du bus n∞" + this.monBus.idBus + " (" + this.monBus.placesDispo + " / " + this.monBus.placesMaxi + ").");
			this.state.put('D', System.currentTimeMillis());		
		}
	}


	public void run() {
		acheter();

		try {
			sleep(2000);
		} catch (Exception e) {

		}

		prendreBus();
	}
}
