package festival.resources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.FileRepresentation;
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
    
	@Get("html")
	public Representation getUsersHtml() {
		return new FileRepresentation("templates/details.html", MediaType.TEXT_HTML);
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
    	// festivaler_ is set by doInit

        JSONObject festivalierObject = toJson(festivalier_);
        festivalierObject.put("url_stats", getReference().toString() + "/stats");

        JsonRepresentation result = new JsonRepresentation(festivalierObject);
        result.setIndenting(true);
        return result;
    }
    
    
    /**
     * Convert a Festivalier to a JSONObject with id and state
     * @param festivalier
     * @return JSONObject of a festivalier
     * @throws JSONException
     */
    private JSONObject toJson(Festivalier festivalier) throws JSONException{
    	JSONObject festivalierObject = new JSONObject();
    	festivalierObject.put("id", festivalier.getNumFestivalier());    	
    	festivalierObject.put("state", festivalier.getLastStatus());
        return festivalierObject;
    }
	

}
