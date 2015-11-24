package festival.simulation;

import java.util.ArrayList;
import java.util.List;

public class SiteDepart {

	public List<Bus> buses = new ArrayList<Bus>();
	
	public SiteDepart() {
		
	}
	
	public synchronized void monterBus(Festivalier f){
		
		int i = 0;
		for (Bus unBus : buses) {
			i++;
			if (!unBus.isOnTheRoadAgain && unBus.placesDispo <= unBus.placesMaxi && unBus.placesDispo > 0){
				try {
					unBus.prendrePassager();
				} catch (Exception e) {
				}
				
				f.setMonBus(unBus);
				return;
			}
			
			if (buses.size() <= i){
				try{
					wait();
				} catch (Exception e){
					
				}
			}
		}
	}
}
