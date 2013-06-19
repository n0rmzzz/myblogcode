package com.tinywebgears.simplegae.core.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tinywebgears.fw.gae.LocalDatastoreTest;

public class UserRepositoryTest extends LocalDatastoreTest
{
    private UserRepository userRepository;

    @Before
    @Override
    public void setUp()
    {
        super.setUp();
        userRepository = new UserRepository();
    }

    @After
    @Override
    public void tearDown()
    {
        super.tearDown();
    }

    @Test
    public void smokeTest()
    {
        assertNotNull(userRepository);
    }
}
