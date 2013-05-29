package com.test.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class DateTestObjectRowMapper implements RowMapper
{
    private final Calendar calendar;

    public DateTestObjectRowMapper(Calendar calendar)
    {
        this.calendar = calendar;
    }

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        int id = rs.getInt(1);
        Date dateField = rs.getTimestamp(2, calendar);
        Date timestampField = rs.getTimestamp(3, calendar);
        return new DateTestObject(id, dateField, timestampField);
    }
}
