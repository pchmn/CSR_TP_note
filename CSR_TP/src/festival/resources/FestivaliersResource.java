package festival.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import festival.backend.Backend;
import festival.internals.Festivalier;

public class FestivaliersResource extends ServerResource{

	/** Backend. */
	private Backend backend_;

	/**
	 * Constructor.
	 * Call for every single people request.
	 */
	public FestivaliersResource()
	{
		super();
		backend_ = (Backend) getApplication().getContext().getAttributes()
				.get("backend");
	}
	
	/**
	 * Returns the list of all the users
	 *
	 * @return  JSON representation of the users
	 * @throws JSONException
	 */
	@Get("json")
	public Representation getPeople() throws JSONException
	{
		Collection<Festivalier> festivaliers = backend_.getDatabase().getFestivaliers();
		Collection<JSONObject> jsonFestivaliers = new ArrayList<JSONObject>();

		for (Festivalier festivalier : festivaliers)
		{
			JSONObject current = new JSONObject();
			current.put("id", festivalier.getNumFestivalier());
			current.put("url", getReference().toString() + "/" + festivalier.getNumFestivalier());
			jsonFestivaliers.add(current);

		}
		JSONArray jsonArray = new JSONArray(jsonFestivaliers);
		JsonRepresentation result = new JsonRepresentation(jsonArray);
		result.setIndenting(true);
		return result;
	}
	
	/**
	 * Create a user with the data present in the json representation
	 * 
	 * @param json representation of the user to create
	 * @return JSON representation of the newly created user
	 * @throws JSONException
	 */
	@Post("json")
	public Representation createPeople(JsonRepresentation representation)
			throws Exception
	{
		JSONObject object = representation.getJsonObject();
		int nbNewFestivalier = object.getInt("nb_new_users");

		Collection<JSONObject> newFestivaliers = new ArrayList<JSONObject>();
		
		for (int i = 0; i < nbNewFestivalier; i++) {
			JSONObject festivalierObject = new JSONObject();
			
			// Save the user
			Festivalier festivalier = backend_.getDatabase().createPeople();
			festivalierObject.put("id", festivalier.getNumFestivalier());
			festivalierObject.put("url", getReference().toString() + festivalier.getNumFestivalier());
			newFestivaliers.add(festivalierObject);
		}
		
		// generate result
		JSONArray jsonArray = new JSONArray(newFestivaliers);
		JsonRepresentation result = new JsonRepresentation(jsonArray);
		return result;
	}
	
	/**
	 * Create a user with the data present in the json representation
	 * 
	 * @param json representation of the user to create
	 * @return JSON representation of the newly created user
	 * @throws JSONException
	 */
	public Representation createPeople(int nbNewFestivalier) throws Exception {

		Collection<JSONObject> newFestivaliers = new ArrayList<JSONObject>();
		
		for (int i = 0; i < nbNewFestivalier; i++) {
			JSONObject festivalierObject = new JSONObject();
			
			// Save the festivalier
			Festivalier festivalier = backend_.getDatabase().createPeople();
			festivalierObject.put("id", festivalier.getNumFestivalier());
			festivalierObject.put("url", getReference().toString() + festivalier.getNumFestivalier());
			newFestivaliers.add(festivalierObject);
		}
		
		// generate result
		JSONArray jsonArray = new JSONArray(newFestivaliers);
		JsonRepresentation result = new JsonRepresentation(jsonArray);
		return result;
	}
}
