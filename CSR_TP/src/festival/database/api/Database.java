package festival.database.api;

import java.util.Collection;

import festival.internals.Bus;
import festival.internals.Festivalier;

/**
 *
 * Interface to the database.
 *
 * @author msimonin
 *
 */
public interface Database
{

	Collection<Festivalier> getFestivaliers();

	Festivalier createPeople() throws InterruptedException;

	Festivalier getFestivalier(int userId);

	Bus createBus(int nbPlaces) throws InterruptedException;

}
