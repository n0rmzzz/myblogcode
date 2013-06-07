package com.test.dao;

import com.test.AbstractServerTransactionalTest;
import com.test.entity.TestEntity;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration("/com/clarity/um/server/applicationContext.xml")
public class TestEntityDaoTest extends AbstractServerTransactionalTest
{
    @Resource
    TestEntityDao dao;

    @Test
    public void run()
    {
        List entities = dao.getAll();
        System.out.println("Test Entities: " + entities);
        TestEntity entity = dao.findByName("First record");
        System.out.println("Test Entity default: " + entity);
        assertNotNull(entity);
    }
}
