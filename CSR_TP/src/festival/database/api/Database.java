package festival.database.api;

import java.util.Collection;
import java.util.List;

import festival.internals.Billeterie;
import festival.internals.Festivalier;
import festival.internals.SiteDepart;
import festival.internals.User;

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

}
