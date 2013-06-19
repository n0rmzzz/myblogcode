package com.tinywebgears.gaedownload.core.dao;

import java.io.Serializable;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Key;
import com.tinywebgears.gaedownload.core.model.MyFile;

public class FileRepository implements Serializable
{
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    // It is faster to have a local copy of persistence manager, but it doesn't
    // work properly in GAE.
    // private PersistenceManagerFactory pmfInstance = PMFHolder.get();

    public MyFile getByKey(Key key) throws DataPersistenceException
    {
        PersistenceManagerFactory pmfInstance = PMFHolder.get();
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        MyFile myfile = null;
        try
        {
            myfile = pm.getObjectById(MyFile.class, key);
        }
        catch (Throwable e)
        {
            throw new DataPersistenceException(e.getLocalizedMessage(), e);
        }
        finally
        {
            pm.close();
        }
        return myfile;
    }

    public void persist(MyFile myfile) throws DataPersistenceException
    {
        PersistenceManagerFactory pmfInstance = PMFHolder.get();
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try
        {
            logger.debug("Saving file: " + myfile.getName());
            pm.makePersistent(myfile);
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
}
