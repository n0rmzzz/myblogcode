package com.test;

import com.test.dao.CustomerDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OracleDateJpaTest
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        CustomerDAO customerDAO = (CustomerDAO)context.getBean("customerDAO");

        customerDAO.testDate();
    }
}
