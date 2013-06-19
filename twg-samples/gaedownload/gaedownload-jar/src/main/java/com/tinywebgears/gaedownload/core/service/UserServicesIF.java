package com.tinywebgears.gaedownload.core.service;

import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.tinywebgears.gaedownload.core.dao.DataPersistenceException;
import com.tinywebgears.gaedownload.core.model.User;
import com.vaadin.terminal.Resource;

public interface UserServicesIF extends ServiceIF
{
    User getUserByKey(Key userKey) throws DataPersistenceException;

    User setUser(String username, String email, String nickname) throws DataPersistenceException;

    User updateUserLoginDate(Key userKey, Date lastLoginDate) throws DataPersistenceException;

    void removeAllFiles();

    Resource serveTextFile(String filename, byte[] text) throws ServiceException;
}
