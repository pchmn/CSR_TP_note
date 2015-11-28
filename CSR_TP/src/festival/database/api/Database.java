package festival.database.api;

import java.util.Collection;
import java.util.List;

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

    /**
     *
     * Create a new user in the database.
     *
     * @param name      The name of the user
     * @param age       The age of the user
     * @return  the new user.
     */
    User createUser(String name, int age)  throws InterruptedException;


    /**
     *
     * Returns the list of users.
     *
     * @return the list of users
     */
    Collection<User> getUsers();


    /**
     * Returns the user with a given id.
     * 
     * @param id	The user id
     * @return	The user or null if not found.
     */
    User getUser(int id);


	/**
	 * 
	 * Deletes the user from the DB.
	 * 
	 * @param user		The user.
	 */
	User deleteUser(User user);

}
