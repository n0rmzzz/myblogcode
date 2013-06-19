package com.tinywebgears.fw.gae;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public abstract class LocalDatastoreTest
{
    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    public void setUp()
    {
        helper.setUp();
    }

    public void tearDown()
    {
        helper.tearDown();
    }
}
