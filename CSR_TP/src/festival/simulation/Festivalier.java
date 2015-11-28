package festival.simulation;

import java.util.HashMap;


public class Festivalier extends Thread {

	private int numFestivalier;
	private HashMap<Character, Long> status = new HashMap<Character, Long>();
	private Bus monBus;
	private Billeterie maBilleterie;
	private SiteDepart siteDepart;

	
	
	public HashMap<Character, Long> getStatus() {
		return status;
	}

	public void setStatus(HashMap<Character, Long> status) {
		this.status = status;
	}

	public int getNumFestivalier() {
		return numFestivalier;
	}

	public void setNumFestivalier(int numFestivalier) {
		this.numFestivalier = numFestivalier;
	}

	public Billeterie getMaBilleterie() {
		return maBilleterie;
	}

	public void setMaBilleterie(Billeterie maBilleterie) {
		this.maBilleterie = maBilleterie;
	}

	public SiteDepart getSiteDepart() {
		return siteDepart;
	}

	public void setSiteDepart(SiteDepart siteDepart) {
		this.siteDepart = siteDepart;
	}

	public Bus getMonBus() {
		return monBus;
	}

	public Festivalier(int numFestivalier, Billeterie billeterie, SiteDepart siteDepart) {
		this.maBilleterie = billeterie;
		this.numFestivalier = numFestivalier;
		this.siteDepart = siteDepart;
		this.status.put('A', System.currentTimeMillis());
		System.out.println("STATE A - Le festivalier " + this.numFestivalier + " est en route");
	}
	
	public void setMonBus(Bus bus){
		this.monBus = bus;
		if(bus != null) {
			System.out.println("Le festivalier " + this.numFestivalier + " a pris le bus " + this.monBus.getId());	
		}
	}

	// Le festivalier achete un ticket
	public void acheter(){
		try {
			this.maBilleterie.vendre();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("STATE B - Le festivalier " + this.numFestivalier + " a achet� sa place");
		this.status.put('B', System.currentTimeMillis());
	}

	public void prendreBus(){
		
		// On vérifie qu'il possède bien un ticket
		if (this.status.containsKey('B')) {
			
			this.siteDepart.monterBus(this);
			System.out.println("STATE C - Le festivalier " + this.numFestivalier + " monte dans le bus n�" + this.monBus.getId() + " (" + this.monBus.getPlacesDispo() + " / " + this.monBus.getPlacesMaxi() + ").");
			this.status.put('C', System.currentTimeMillis());
		}
	}

	public synchronized void sortirBus(){
		
		// On vérifie qu'il était bien dans le bus
		if (this.status.containsKey('C')) {
			while (!this.monBus.isOnTheRoadAgain()){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("STATE D - Le festivalier " + this.numFestivalier + " sort du bus n�" + this.monBus.getId() + " (" + this.monBus.getPlacesDispo() + " / " + this.monBus.getPlacesMaxi() + ").");
			this.status.put('D', System.currentTimeMillis());		
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
