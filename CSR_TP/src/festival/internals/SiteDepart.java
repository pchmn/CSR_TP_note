package festival.internals;

import java.util.ArrayList;
import java.util.List;

public class SiteDepart {

	public List<Bus> buses;
	
	public SiteDepart() {
		buses = new ArrayList<Bus>();
	}
	
	/**
	 * Attribue un bus a un festivalier
	 * @param f
	 */
	public synchronized void monterBus(Festivalier f){
		
		// Tant qu'aucun bus ne lui est attribué
		while(true) {
			// TODO Concurrent ModificationException
			// Parcours les différents bus
			for (Bus unBus : buses) {
				
				// Vérifie leurs disponibilités
				if (!unBus.isOnTheRoadAgain() && unBus.getPlacesDispo() <= unBus.getPlacesMaxi() && unBus.getPlacesDispo() > 0){
					
					// Essaie d'attribuer un bus au festivalier
					try {
						unBus.prendrePassager(f);
					} catch (Exception e) {
					}
					
					f.setMonBus(unBus);
					return;
				}
			}	
		}
	}
}
