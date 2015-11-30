package festival.database.api;

import java.util.Collection;

import festival.internals.Billeterie;
import festival.internals.Bus;
import festival.internals.Festivalier;
import festival.internals.SiteDepart;

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

	Festivalier createPeople(Billeterie billeterie, SiteDepart siteDepart) throws InterruptedException;

	Festivalier getFestivalier(int userId);

	Bus createBus(int nbPlaces) throws InterruptedException;

}
