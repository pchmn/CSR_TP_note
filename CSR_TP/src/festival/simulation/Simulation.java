package festival.simulation;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

	public List<Bus> mesBus;
	public List<Festivalier> mesFestivaliers;
	public Billeterie maBilleterie;
	public SiteDepart siteDepart = new SiteDepart();

	public Simulation() {
	    this.mesBus = new ArrayList<Bus>();
	    this.mesFestivaliers = new ArrayList<Festivalier>();
	    this.maBilleterie = new Billeterie();
    }

	public void addPeople(int people) {
        for (int i = 0; i < people; i++) {
        	Festivalier festivalier = new Festivalier(i, maBilleterie, siteDepart);
			this.mesFestivaliers.add(festivalier);
			festivalier.start();
			System.out.println("Le festivalier " + festivalier.numFestivalier + " est parti.");
		}
    }

	public void addBuses(int buses, int seats) {
		for (int i = 0; i < buses; i++) {
			Bus bus = new Bus(i, seats);
			this.mesBus.add(bus);
			this.siteDepart.buses.add(bus);
			bus.start();
			System.out.println("Le bus " + bus.idBus + " est parti.");
		}
    }
}
