package com.test.dao;

import com.test.model.DateTestObjectRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class JdbcCustomerDAO extends JdbcDaoSupport implements CustomerDAO
{
    public void testDate()
    {
        String sql = "SELECT ID, DATE_COLUMN, TIMESTAMP_COLUMN FROM DATE_TEST_TABLE ORDER BY ID";
        List<?> objects = getJdbcTemplate().queryForList(sql);
        System.out.println("Objects:");
        printList(objects);
        List<?> objectsMapped = getJdbcTemplate().query(sql, new DateTestObjectRowMapper(null));
        System.out.println("Objects (using row mapper):");
        printList(objectsMapped);
        List<?> objectsMappedGMT = getJdbcTemplate().query(sql, new DateTestObjectRowMapper(Calendar.getInstance(
            TimeZone.getTimeZone("GMT"))));
        System.out.println("Objects (using row mapper and GMT calendar):");
        printList(objectsMappedGMT);
    }

    private void printList(List<?> list)
    {
        for (Object item : list)
            System.out.println("\t" + item);
        System.out.println("--");
    }
}
