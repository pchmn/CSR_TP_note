package festival.database.api.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import festival.database.api.Database;
import festival.internals.Billeterie;
import festival.internals.Bus;
import festival.internals.Festivalier;
import festival.internals.SiteDepart;

/**
 *
 * In-memory database 
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class InMemoryDatabase implements Database
{
    
    /** Festivalier count (next id to give).*/
    private int festivalierCount_;

    /** Festivalier Hashmap. */
    Map<Integer, Festivalier> festivaliers_;
    
    /** Bus count (next id to give).*/
    private int busCount_;

    /** Bus Hashmap. */
    Map<Integer, Bus> buses_;

    private Billeterie billeterie;
	private SiteDepart siteDepart;
	
    public InMemoryDatabase() {
    	billeterie = new Billeterie();
    	siteDepart = new SiteDepart();
        festivaliers_ = new HashMap<Integer, Festivalier>();
        buses_ = new HashMap<Integer, Bus>();
    }


    /**
     * Créer un nouveau festivalier, le rajoute a database et démarre le thread
     */
    @Override
    public synchronized Festivalier createPeople() throws InterruptedException{
    	Festivalier festivalier = new Festivalier(billeterie, siteDepart);
        festivalier.setNumFestivalier(festivalierCount_);
        festivaliers_.put(festivalierCount_, festivalier);
        festivalier.start();
        Thread.sleep(100);
        festivalierCount_ ++;
        return festivalier;
    }

	@Override
	public Collection<Festivalier> getFestivaliers() {
		return festivaliers_.values();
	}
	
	public Festivalier getFestivalier(int id){
		return festivaliers_.get(id);
	}


	/**
	 * Créer un bus, le rajoute a database et lance le thread
	 */
	@Override
	public synchronized Bus createBus(int nbPlaces) throws InterruptedException{
		Bus bus = new Bus(nbPlaces);
        bus.setIdBus(busCount_);
        buses_.put(busCount_, bus);
		siteDepart.buses.add(bus);
        bus.start();
        Thread.sleep(100);
        busCount_ ++;
        return bus;
	}
}
