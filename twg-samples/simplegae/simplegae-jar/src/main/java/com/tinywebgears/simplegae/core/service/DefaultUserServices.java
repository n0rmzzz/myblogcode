package com.tinywebgears.simplegae.core.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Key;
import com.tinywebgears.simplegae.core.dao.DataPersistenceException;
import com.tinywebgears.simplegae.core.dao.UserRepository;
import com.tinywebgears.simplegae.core.model.User;

public class DefaultUserServices extends AbstractService implements UserServicesIF
{
    private final Logger logger = LoggerFactory.getLogger(DefaultUserServices.class);

    private final UserRepository userRepo;

    public DefaultUserServices(UserRepository userRepo)
    {
        super(userRepo);
        this.userRepo = userRepo;
    }

    @Override
    public User getUserByKey(Key userKey) throws DataPersistenceException
    {
        return userRepo.getByKey(userKey);
    }

    @Override
    public User setUser(String username, String email, String nickname) throws DataPersistenceException
    {
        logger.info("setUser - username: " + username + " email: " + email + " nickname: " + nickname);
        if (username == null)
            user = null;
        else
        {
            user = userRepo.getByName(username);
            if (user == null)
            {
                user = new User(username, email, nickname, null);
                userRepo.persist(user);
            }
        }
        return user;
    }

    @Override
    public User updateUserLoginDate(Key userKey, Date lastLoginDate) throws DataPersistenceException
    {
        logger.info("updateUserLoginDate - key: " + userKey + " last login date: " + lastLoginDate);
        if (userKey == null)
            return null;
        else
        {
            user = userRepo.getByKey(userKey);
            if (user == null)
                throw new DataPersistenceException("No user found.");
            user.setLastLoginDate(lastLoginDate);
            userRepo.persist(user);
        }
        return user;
    }
}
