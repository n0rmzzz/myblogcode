package com.tinywebgears.simplegae.core.service;

import java.io.Serializable;

import com.tinywebgears.simplegae.core.dao.DataPersistenceException;
import com.tinywebgears.simplegae.core.model.User;

public interface ServiceIF extends Serializable
{
    void setUser(User user) throws DataPersistenceException;

    void clearUser() throws DataPersistenceException;
}
