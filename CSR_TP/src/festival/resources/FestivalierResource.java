package festival.resources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import festival.backend.Backend;
import festival.internals.Festivalier;

public class FestivalierResource extends ServerResource{
	

	private Festivalier festivalier_;
	
	/** Backend. */
	private Backend backend_;

	/**
	 * Constructor.
	 * Call for every single people request.
	 */
	public FestivalierResource()
	{
		super();
		backend_ = (Backend) getApplication().getContext().getAttributes()
				.get("backend");
	}
	
	/* 
     * The method doInit is called prior to the others.
     */
    @Override
    protected void doInit() throws ResourceException 
    {
        // On récupère l'id passée dans l'URL
        // Note : a priori le cast ne passe pas en java6
        //int userId = (Integer) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf((String) getRequest().getAttributes().get("userId"));
        festivalier_ = backend_.getDatabase().getFestivalier(userId);
        if (festivalier_ == null)
        {
            getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        }
    }
    
    /**
     * Returns the user matching the id given in the URI
     * 
     * @return JSON representation of a user
     * @throws JSONException
     */
    @Get("json")
    public Representation getFestivalier() throws JSONException 
    {
    	// user_ is set by doInit

        JSONObject festivalierObject = toJson(festivalier_);
        festivalierObject.put("url_stats", getReference().toString() + "/stats");

        JsonRepresentation result = new JsonRepresentation(festivalierObject);
        result.setIndenting(true);
        return result;
    }
    
    private JSONObject toJson(Festivalier festivalier) throws JSONException{
    	JSONObject festivalierObject = new JSONObject();
    	festivalierObject.put("id", festivalier.getNumFestivalier());    	
    	festivalierObject.put("state", getStatus(festivalier.getStatus()));
        return festivalierObject;
    }
	
    /**
     * R�cup�re le dernier status d'un liste de status
     * @param statusFestivalier
     * @return l'�tat du status
     */
	private Character getStatus(HashMap<Character, Long> statusFestivalier ){
		Character status = 'N';
		for (Entry<Character, Long> entry : statusFestivalier.entrySet())
		{
			status = entry.getKey();
		}
    	return status;
	}
}
