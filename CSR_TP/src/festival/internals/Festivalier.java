package festival.internals;

import java.util.HashMap;
import java.util.Map.Entry;

public class Festivalier extends Thread {

	private int numFestivalier;
	private HashMap<Character, Long> status = new HashMap<Character, Long>();
	private Bus monBus;
	private Billeterie maBilleterie;
	private SiteDepart siteDepart;

	
/**
 * ---- Getters and Setters ----
 */
	
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
	
	public void setMonBus(Bus bus){
		this.monBus = bus;
	}
	
/**
 * ---- ------------ ----
 */

	/**
	 * Constructeur pour simulation
	 * @param numFestivalier
	 * @param billeterie
	 * @param siteDepart
	 */
	public Festivalier(int numFestivalier, Billeterie billeterie, SiteDepart siteDepart) {
		this.maBilleterie = billeterie;
		this.numFestivalier = numFestivalier;
		this.siteDepart = siteDepart;
		this.status.put('A', System.currentTimeMillis());
	}
	
	/**
	 * Constructeur pour database, id rajouté apres
	 * @param billeterie
	 * @param siteDepart
	 */
	public Festivalier(Billeterie billeterie, SiteDepart siteDepart) {
		this.maBilleterie = billeterie;
		this.numFestivalier = 0;
		this.siteDepart = siteDepart;
		this.status.put('A', System.currentTimeMillis());
	}

	/**
	 * Le festivalier achete un billet
	 */
	public void acheter(){
		
		// Achete un billet si il reste des place
		try {
			this.maBilleterie.vendre();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.status.put('B', System.currentTimeMillis());
		System.out.println("STATE B - Le festivalier " + this.numFestivalier + " a acheté sa place");
	}

	/**
	 * Attribut un bus au festivalier
	 */
	public void prendreBus(){
		
		// On vÃ©rifie qu'il possÃ¨de bien un ticket
		if (this.status.containsKey('B')) {
			
			this.siteDepart.monterBus(this);
			this.status.put('C', System.currentTimeMillis());
			System.out.println("STATE C - Le festivalier " + this.numFestivalier + " monte dans le bus n°" + this.monBus.getIdBus() + " (" + this.monBus.getPlacesDispo() + " / " + this.monBus.getPlacesMaxi() + ").");
		}
	}
	
    /**
     * Récupère le dernier status d'un liste de status
     * @param statusFestivalier
     * @return l'état du status
     */
	public Character getLastStatus(){
		Character status = 'N';
		for (Entry<Character, Long> entry : this.status.entrySet())
		{
			status = entry.getKey();
		}
    	return status;
	}


	public void run() {
		
		// Simule la marche jusqu'à la billeterie
		try {
			sleep(2000);
		} catch (Exception e) {

		}
		
		acheter();

		// Simule la marche jusqu'au bus
		try {
			sleep(2000);
		} catch (Exception e) {

		}

		prendreBus();
	}
}
