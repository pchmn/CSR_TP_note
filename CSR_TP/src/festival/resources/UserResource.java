package festival.resources;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import festival.backend.Backend;
import festival.internals.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing a user
 *
 * @author msimonin
 * @author ctedeschi
 *
 */
public class UserResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /** User handled by this resource. */
    private User user_;

    
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
        user_ = backend_.getDatabase().getUser(userId);
        if (user_ == null)
        {
            getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        }
    }
    
    /**
     * Constructor.
     * Call for every single user request.
     */
    public UserResource()
    {
        backend_ = (Backend) getApplication().getContext().getAttributes()
                .get("backend");
    }

    /**
     * Returns the user matching the id given in the URI
     * 
     * @return JSON representation of a user
     * @throws JSONException
     */
    @Get("json")
    public Representation getUser() throws JSONException 
    {
    	// user_ is set by doInit

        JSONObject userObject = toJson(user_);
        userObject.put("tweet_url", getReference().toString() + "/tweets");

        JsonRepresentation result = new JsonRepresentation(userObject);
        result.setIndenting(true);
        return result;
    }
    
    @Delete("json")
    public Representation deleteUser() throws JSONException
    {
    	User deletedUser = backend_.getDatabase().deleteUser(user_);
    	JSONObject jsonDeletedUser = toJson(deletedUser); 
        jsonDeletedUser.put("status", "deleted");
        JsonRepresentation result = new JsonRepresentation(jsonDeletedUser);
        result.setIndenting(true);
        return result;
    }
    
    
    JSONObject toJson(User user) throws JSONException{
    	JSONObject userObject = new JSONObject();
        userObject.put("name", user.getName());
        userObject.put("age", user.getAge());
        userObject.put("id", user.getId());
        return userObject;
    }

}
