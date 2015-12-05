package festival.main;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

import festival.application.MyFestivalApplication;
import festival.backend.Backend;

/**
 * Main RESTlet minimal example
 *
 * @author msimonin
 */
public final class Main
{
	
	/** Hide constructor. */
    private Main()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Main method.
     *
     * @param args  The arguments of the command line
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        // Create a component
        Component component = new Component();
        Context context = component.getContext().createChildContext();
        component.getServers().add(Protocol.HTTP, 5000);
        component.getClients().add(Protocol.FILE);

        // Create an application
        Application application = new MyFestivalApplication(context);

        // Add the backend into component's context
        Backend backend = new Backend();
        context.getAttributes().put("backend", backend);
        component.getDefaultHost().attach(application);
        
        // Start the component
        component.start();
       
        
        // Run a simulation
        int nbFestivaliers = 10;
        int nbBus = 2;
        int nbPlacesBus = 3;
        
        int i = 0;
        while(i <= nbFestivaliers){
            backend.getDatabase().createPeople();
        	i++;
        }
        
        int j = 0;
        while(j <= nbBus){
            backend.getDatabase().createBus(nbPlacesBus);
        	j++;
        }
    }

}
