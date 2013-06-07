package com.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

@RunWith(value = SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager")
@TestExecutionListeners(
    {TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@Transactional
public abstract class AbstractServerTransactionalTest
{
    @NotNull
    @PersistenceContext(unitName = "punit")
    protected EntityManager entityManager;
}
