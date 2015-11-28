package festival.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import festival.backend.Backend;
import festival.internals.Bus;
import festival.internals.Tweet;
import festival.internals.User;

public class BusesResource extends ServerResource {

	/** Backend. */
	private Backend backend_;

	/** User handled by this resource. */
	private User user_;

	/**
	 * Constructor. Call for every single user request.
	 */
	public BusesResource() {
		super();
		backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
	}

	/*
	 * The method doInit is called prior to the others.
	 */
	@Override
	protected void doInit() throws ResourceException {
		// On récupère l'id passée dans l'URL
		// Note : a priori le cast ne passe pas en java6
		// int userId = (Integer) getRequest().getAttributes().get("userId");
		int userId = Integer.valueOf((String) getRequest().getAttributes().get("userId"));
		user_ = backend_.getDatabase().getUser(userId);
		if (user_ == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}

	@Get("html")
	public Representation getUsersHtml() {
		return new FileRepresentation("templates/list-tweets.html", MediaType.TEXT_HTML);
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
		Bus bus = new Bus(object.getInt("nb_places"));
		JSONObject jsonBus = toJson(bus);
		JsonRepresentation result = new JsonRepresentation(jsonBus);
		result.setIndenting(true);
		return result;
	}

	private JSONObject toJson(Bus bus) throws JSONException {
		JSONObject current = new JSONObject();
		current.put("nb_places", bus.getPlacesMaxi());
		return current;
	}
}
