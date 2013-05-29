package com.test.model;

import java.util.Date;

public class DateTestObject
{
    private final int id;
    private final Date dateField;
    private final Date timestampField;

    public DateTestObject(int id, Date dateField, Date timestampField)
    {
        this.id = id;
        this.dateField = dateField;
        this.timestampField = timestampField;
    }

    @Override
    public String toString()
    {
        return "DateTestObject [date_field=" + dateField + ", time_field=" + timestampField + ", id=" + id + "]";
    }
}
