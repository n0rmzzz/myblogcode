package com.tinywebgears.gaedownload.core.dao;

import java.io.Serializable;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMFHolder implements Serializable
{
    private static final PersistenceManagerFactory pmfInstance;
    private static final PersistenceManagerFactory pmfInstanceTransactional;
    static
    {
        pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
        pmfInstanceTransactional = JDOHelper.getPersistenceManagerFactory("transactions-mandatory");
    }

    private PMFHolder()
    {
    }

    public static PersistenceManagerFactory get()
    {
        return pmfInstance;
    }

    public static PersistenceManagerFactory getTransactional()
    {
        return pmfInstanceTransactional;
    }
}
