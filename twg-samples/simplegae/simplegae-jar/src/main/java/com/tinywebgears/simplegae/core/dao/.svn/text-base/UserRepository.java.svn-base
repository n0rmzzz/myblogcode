package com.tinywebgears.simplegae.core.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Key;
import com.tinywebgears.simplegae.core.model.User;

public class UserRepository implements Serializable
{
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    // It is faster to have a local copy of persistence manager, but it doesn't work properly in GAE.
    // private PersistenceManagerFactory pmfInstance = PMFHolder.get();

    public Collection<User> getAll() throws DataPersistenceException
    {
        PersistenceManagerFactory pmfInstance = PMFHolder.get();
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try
        {
            List<User> users = new ArrayList<User>();

            Extent<User> extent = pm.getExtent(User.class, true);
            for (User user : extent)
                users.add(user);
            extent.closeAll();

            return users;
        }
        catch (Throwable e)
        {
            throw new DataPersistenceException(e.getLocalizedMessage(), e);
        }
        finally
        {
            pm.close();
        }
    }

    public User getByKey(Key key) throws DataPersistenceException
    {
        PersistenceManagerFactory pmfInstance = PMFHolder.get();
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        User user = null;
        try
        {
            user = pm.getObjectById(User.class, key);
        }
        catch (Throwable e)
        {
            throw new DataPersistenceException(e.getLocalizedMessage(), e);
        }
        finally
        {
            pm.close();
        }
        return user;
    }

    public User getByName(String username) throws DataPersistenceException
    {
        logger.info("Loading user: " + username);
        PersistenceManagerFactory pmfInstance = PMFHolder.get();
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try
        {
            Query query = pm.newQuery(User.class);
            String filterString = "username == usernameParam";
            String paramsString = String.class.getName() + " usernameParam";
            Map<String, Object> values = new HashMap<String, Object>();
            values.put("usernameParam", username);
            query.declareParameters(paramsString);
            query.setFilter(filterString.toString());
            query.setUnique(true);
            User user = (User) query.executeWithMap(values);
            logger.debug("User found: " + user);
            return user;
        }
        finally
        {
            pm.close();
        }
    }

    public User persist(User user) throws DataPersistenceException
    {
        PersistenceManagerFactory pmfInstance = PMFHolder.get();
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try
        {
            logger.info("Saving user: " + user);
            pm.makePersistent(user);
        }
        catch (Throwable e)
        {
            throw new DataPersistenceException(e.getLocalizedMessage(), e);
        }
        finally
        {
            pm.close();
        }
        return user;
    }
}
