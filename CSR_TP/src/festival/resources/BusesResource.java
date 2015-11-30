package festival.resources;

import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import festival.backend.Backend;
import festival.internals.Bus;

public class BusesResource extends ServerResource {

	/** Backend. */
	private Backend backend_;

	/**
	 * Constructor. Call for every single user request.
	 */
	public BusesResource() {
		super();
		backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
	}


	/**
	 * Create a bus
	 * 
	 * @param json
	 *            representation of the bus to create
	 * @return JSON representation of the newly created bus
	 * @throws JSONException
	 */
	@Post("json")
	public Representation createBus(JsonRepresentation representation) throws Exception {
		JSONObject object = representation.getJsonObject();
		int nbBuses = object.getInt("nb_new_buses");
		int nbPlaces = object.getInt("nb_places");

		Collection<JSONObject> newBuses = new ArrayList<JSONObject>();
		
		for (int i = 0; i < nbBuses; i++) {
			JSONObject busObject = new JSONObject();
			
			// Save the bus
			Bus bus = backend_.getDatabase().createBus(nbPlaces);
			busObject.put("id", bus.getIdBus());
			busObject.put("nbPlaces", bus.getPlacesMaxi());
			
			newBuses.add(busObject);
		}
		
		// generate result
		JSONArray jsonArray = new JSONArray(newBuses);
		JsonRepresentation result = new JsonRepresentation(jsonArray);
		return result;
	}
}
