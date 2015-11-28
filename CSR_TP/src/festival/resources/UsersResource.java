package festival.resources;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.FileRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import festival.backend.Backend;
import festival.internals.User;

/**
 * Resource exposing the users
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class UsersResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public UsersResource()
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
    public Representation getUsers() throws JSONException
    {
        Collection<User> users = backend_.getDatabase().getUsers();
        Collection<JSONObject> jsonUsers = new ArrayList<JSONObject>();

        for (User user : users)
        {
            JSONObject current = new JSONObject();
            current.put("id", user.getId());
            current.put("name", user.getName());
            current.put("age", user.getAge());
            current.put("url", getReference().toString() + user.getId());
            current.put("tweet_url", getReference().toString() + user.getId() + "/tweets");
            jsonUsers.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonUsers);
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
    public Representation createUser(JsonRepresentation representation)
        throws Exception
    {
        JSONObject object = representation.getJsonObject();
        String name = object.getString("name");
        int age = object.getInt("age");

        // Save the user
        User user = backend_.getDatabase().createUser(name, age);

        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("name", user.getName());
        resultObject.put("age", user.getAge());
        resultObject.put("id", user.getId());
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }

}
