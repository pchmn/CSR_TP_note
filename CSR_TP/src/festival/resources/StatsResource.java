package festival.resources;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import festival.backend.Backend;
import festival.internals.Festivalier;

public class StatsResource extends ServerResource{

	/** Backend. */
	private Backend backend_;

	/**
	 * Constructor.
	 * Call for every single people request.
	 */
	public StatsResource(){
		super();
		backend_ = (Backend) getApplication().getContext().getAttributes()
				.get("backend");
	}
	
	/**
	 * Get the number of festivalier in each stats and the average time to go to the last state  
	 * @return JSON Representation of the global stats
	 * @throws JSONException
	 */
	@Get("json")
	public Representation getStats() throws JSONException {
		
		// Récupère tous les festivaliers
		Collection<Festivalier> festivaliers = backend_.getDatabase().getFestivaliers();

		// Map des différents etats avec le nombre de festivalier a cette état
		HashMap<Character, Integer> states = new HashMap<Character, Integer>();
		Long tempsTotal = new Long(0);
		int nbFestivalierArrive = 0;
		
		//Parcours tous les festivaliers
		for (Festivalier f : festivaliers){
			
			// Fait +1 pour le dernier etat du festivalier
			Character lastStatus = f.getLastStatus();
			int lastValue = 0;
			
			// Si l'état existe déja, récupère sa valeur
			if (states.containsKey(lastStatus)){
				lastValue = states.get(lastStatus).intValue();
			}
			states.put(lastStatus, lastValue+1);
			
			// Calcul du temps moyen
			if (lastStatus == 'D'){
				tempsTotal += f.getStatus().get('D') - f.getStatus().get('A');
				nbFestivalierArrive++;
			}
		}		
		
		Long tempsMoyen = new Long(0);
		// Si il y a des festivaliers en state D, calcul le temps moyen
		if (nbFestivalierArrive > 0){
			tempsMoyen = tempsTotal / nbFestivalierArrive;
		}
		
		JSONArray jsonArray = statesToJson(states); 
		
		JSONObject statsObject = new JSONObject();
		statsObject.put("states", jsonArray);
		statsObject.put("temps", tempsMoyen);
		
		JsonRepresentation result = new JsonRepresentation(statsObject);
		result.setIndenting(true);
		return result;
	}
	
	/**
	 * Convertit un HashMap en JSONArray avec les champs state et nb
	 * @param states
	 * @return
	 * @throws JSONException
	 */
	private JSONArray statesToJson(HashMap<Character, Integer> states) throws JSONException{
		JSONArray jsonArray = new JSONArray();
		
		// Parcours les différents états
		for (Entry<Character, Integer> entry : states.entrySet()){
			JSONObject statObject = new JSONObject();
			statObject.put("nb", entry.getValue());
			statObject.put("state", entry.getKey());
			jsonArray.put(statObject);
		}
		return jsonArray;
	}
}
