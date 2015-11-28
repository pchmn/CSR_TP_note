package festival.simulation;

import java.util.ArrayList;
import java.util.List;

public class SiteDepart {

	public List<Bus> buses = new ArrayList<Bus>();
	
	public SiteDepart() {
		
	}
	
	public synchronized void monterBus(Festivalier f){
		
		while(true) {
			for (Bus unBus : buses) {
				if (!unBus.isOnTheRoadAgain() && unBus.getPlacesDispo() <= unBus.getPlacesMaxi() && unBus.getPlacesDispo() > 0){
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
