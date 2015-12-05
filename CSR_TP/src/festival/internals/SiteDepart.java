package festival.internals;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SiteDepart {

	public List<Bus> buses;

	public SiteDepart() {
		buses = new CopyOnWriteArrayList<Bus>();
	}

	/**
	 * Attribue un bus a un festivalier
	 * @param f
	 */
	public synchronized void monterBus(Festivalier f){
		
		// Tant qu'aucun bus ne lui est attribue
		while(true) {
			// TODO Concurrent ModificationException
			// Parcours les differents bus
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
