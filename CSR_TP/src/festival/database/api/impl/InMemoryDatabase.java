package festival.database.api.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import festival.database.api.Database;
import festival.internals.User;

/**
 *
 * In-memory database 
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class InMemoryDatabase implements Database
{

    /** User count (next id to give).*/
    private int userCount_;

    /** User Hashmap. */
    Map<Integer, User> users_;


    public InMemoryDatabase()
    {
        users_ = new HashMap<Integer, User>();
    }

    /**
     *
     * Synchronized user creation.
     * @param name
     * @param age
     *
     * @return the user created
     * @throws InterruptedException 
     */
    @Override
    public synchronized User createUser(String name, int age) throws InterruptedException
    {
        User user = new User(name, age);
        user.setId(userCount_);
        users_.put(userCount_, user);
        Thread.sleep(100);
        userCount_ ++;
        return user;
    }

    @Override
    public Collection<User> getUsers()
    {
        return users_.values();
    }

    @Override
    public User getUser(int id)
    {
        return users_.get(id);
    }

	@Override
	public User deleteUser(User user) {
		// TODO Auto-generated method stub
		return users_.remove(user.getId());
	}

}
