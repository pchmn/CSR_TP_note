package festival.application;




import java.io.File;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Reference;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import festival.resources.RootResource;
import festival.resources.TweetsResource;
import festival.resources.UserResource;
import festival.resources.UsersResource;

/**
 *
 * Application.
 *
 * @author msimonin
 *
 */
public class MyTwitterApplication extends Application
{

    public MyTwitterApplication(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot()
    {
    	File staticDirectory = new File("static/");
    	Directory directory = new Directory(getContext(), "file:///" + staticDirectory.getAbsolutePath() + "/");
    	directory.isDeeplyAccessible();
    	directory.isListingAllowed();
    	    	
        Router router = new Router(getContext());
        router.attach("/", RootResource.class);
        router.attach("/static", directory);
        router.attach("/users", UsersResource.class);
        router.attach("/users/", UsersResource.class);
        router.attach("/users/{userId}", UserResource.class);
        router.attach("/users/{userId}/", UserResource.class);
        router.attach("/users/{userId}/tweets", TweetsResource.class);
        router.attach("/users/{userId}/tweets/", TweetsResource.class);
        return router;
    }
}
