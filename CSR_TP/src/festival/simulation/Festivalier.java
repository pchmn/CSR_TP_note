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
	}
	
	public void setMonBus(Bus bus){
		this.monBus = bus;
	}

	// Le festivalier achete un ticket
	public void acheter(){
		try {
			this.maBilleterie.vendre();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.state.put('B', System.currentTimeMillis());
	}

	public void prendreBus(){
		
		// On vérifie qu'il possède bien un ticket
		if (this.state.containsKey('B')) {
			Bus bus = new Bus();
			
			this.setMonBus(bus);
			
			try {
				this.siteDepart.monterBus(this);			
			} catch (Exception e) {
				e.printStackTrace();
			}

			this.state.put('C', System.currentTimeMillis());
		}
	}

	public void sortirBus(){
		
		// On vérifie qu'il était bien dans le bus
		if (this.state.containsKey('C')) {
			this.state.put('D', System.currentTimeMillis());		
		}
	}


	public void run() {
		acheter();

		try {
			sleep(10);
		} catch (Exception e) {

		}

		prendreBus();

		try {
			// TODO Set the real sleeptime
			sleep(1000);
		} catch (Exception e) {

		}

		sortirBus();
	}
}
