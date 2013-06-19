package com.tinywebgears.simplegae.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Key;
import com.tinywebgears.simplegae.core.dao.DataPersistenceException;
import com.tinywebgears.simplegae.core.dao.UserRepository;
import com.tinywebgears.simplegae.core.model.User;

public abstract class AbstractService implements ServiceIF
{
    private final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    private final UserRepository userRepo;
    protected User user;

    public AbstractService(UserRepository userRepo)
    {
        this.userRepo = userRepo;
    }

    @Override
    public void setUser(User user) throws DataPersistenceException
    {
        logger.info("setUser - user: " + user);
        this.user = user;
    }

    @Override
    public void clearUser() throws DataPersistenceException
    {
        setUser(null);
    }

    protected User getUser() throws DataPersistenceException
    {
        return user;
    }

    protected Key getUserKey() throws DataPersistenceException
    {
        if (user == null)
            throw new DataPersistenceException("No user found!");
        return user.getKey();
    }

    protected String getUsername() throws ServiceException
    {
        if (user == null)
            throw new ServiceException("No user found!");
        return user.getUsername();
    }

    protected String getEmail() throws ServiceException
    {
        if (user == null)
            throw new ServiceException("No user found!");
        return user.getEmail();
    }

    protected String getNickName() throws ServiceException
    {
        if (user == null)
            throw new ServiceException("No user found!");
        return user.getNickname();
    }

    protected UserRepository getUserRepo()
    {
        return userRepo;
    }
}
