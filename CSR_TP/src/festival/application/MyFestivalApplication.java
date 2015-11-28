package festival.application;




import java.io.File;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Reference;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import festival.resources.RootResource;
import festival.resources.BusesResource;
import festival.resources.PeopleResource;
import festival.resources.UserResource;
import festival.resources.UsersResource;

/**
 *
 * Application.
 *
 * @author msimonin
 *
 */
public class MyFestivalApplication extends Application
{

    public MyFestivalApplication(Context context)
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
        router.attach("/people", PeopleResource.class);
        router.attach("/people/", PeopleResource.class);
        router.attach("/people/{userId}", PeopleResource.class);
        router.attach("/people/{userId}/stats", PeopleResource.class);
        router.attach("/buses", BusesResource.class);
        return router;
    }
}
