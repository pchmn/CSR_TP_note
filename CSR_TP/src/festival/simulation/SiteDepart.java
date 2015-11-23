package festival.simulation;

import java.util.ArrayList;
import java.util.List;

public class SiteDepart {

	public List<Bus> buses = new ArrayList<Bus>();
	
	public SiteDepart() {
		
	}
	
	public synchronized void monterBus(Festivalier f){
		
		for (Bus unBus : buses) {
			if (!unBus.isOnTheRoadAgain && unBus.placesDispo <= unBus.placesMaxi){
				f.setMonBus(unBus);
			}
		} 
	}
}
