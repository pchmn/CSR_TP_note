package festival.database.api.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import festival.database.api.Database;
import festival.internals.Billeterie;
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
    
    /** User count (next id to give).*/
    private int festivalierCount_;

    /** User Hashmap. */
    Map<Integer, Festivalier> festivaliers_;


    public InMemoryDatabase()
    {
        festivaliers_ = new HashMap<Integer, Festivalier>();
    }


    @Override
    public synchronized Festivalier createPeople(Billeterie billeterie, SiteDepart siteDepart) throws InterruptedException
    {
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
}
