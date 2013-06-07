package com.test.dao;

import com.test.entity.TestEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;

@Component("testEntityDao")
public class TestEntityDao
{
    @NotNull
    @PersistenceContext(unitName = "punit")
    private EntityManager entityManager;

    public TestEntityDao()
    {
    }

    public TestEntity findByName(final String name)
    {
        try
        {
            return (TestEntity)entityManager.createQuery("select te from TestEntity te where te.name = :name")
                .setParameter("name", name).getSingleResult();
        }
        catch (NoResultException nre)
        {
            throw new RuntimeException("Could not find account for name: " + name);
        }
    }

    public List getAll()
    {
        try
        {
            return entityManager.createQuery("select te from TestEntity te").getResultList();
        }
        catch (NoResultException nre)
        {
            throw new RuntimeException("Could not find any account");
        }
    }
}
