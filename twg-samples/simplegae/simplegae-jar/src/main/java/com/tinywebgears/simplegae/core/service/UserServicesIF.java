package com.tinywebgears.simplegae.core.service;

import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.tinywebgears.simplegae.core.dao.DataPersistenceException;
import com.tinywebgears.simplegae.core.model.User;

public interface UserServicesIF extends ServiceIF
{
    User getUserByKey(Key userKey) throws DataPersistenceException;

    User setUser(String username, String email, String nickname) throws DataPersistenceException;

    User updateUserLoginDate(Key userKey, Date lastLoginDate) throws DataPersistenceException;
}
