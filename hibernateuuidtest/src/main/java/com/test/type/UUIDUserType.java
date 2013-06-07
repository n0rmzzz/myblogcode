package com.test.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UUIDUserType implements UserType
{
    public static final String CLASS_NAME = "com.test.type.UUIDUserType";

    private static final int[] SQL_TYPES = {Types.BINARY};

    public int[] sqlTypes()
    {
        return SQL_TYPES;
    }

    public Class returnedClass()
    {
        return UUID.class;
    }

    public boolean equals(Object x, Object y)
    {
        if (!(x instanceof UUID && y instanceof UUID))
            return false;
        return x.equals(y);
    }

    public int hashCode(Object o)
    {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o)
        throws HibernateException, SQLException
    {
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor)
        throws HibernateException, SQLException
    {
    }

    public Object deepCopy(Object value)
    {
        if (value == null) return null;
        if (!UUID.class.isAssignableFrom(value.getClass()))
        {
            throw new HibernateException(value.getClass().toString() + " : cast exception");
        }
        UUID other = (UUID)value;
        return UUID.fromBytes(other.toBytes());
    }

    public boolean isMutable()
    {
        return true;
    }

    public Serializable disassemble(Object value)
    {
        if (!UUID.class.isAssignableFrom(value.getClass()))
        {
            throw new HibernateException(value.getClass().toString() + " : cast exception");
        }
        UUID uuid = (UUID)value;
        return uuid.toBytes();
    }

    public Object assemble(Serializable cached, Object owner)
    {
        if (cached instanceof byte[])
        {
            return UUID.fromBytes((byte[])cached);
        }
        else
        {
            throw new RuntimeException("Attempt to make a UUID from a non-byte type: " + cached);
        }
    }

    public Object replace(Object original, Object target, Object owner)
    {
        return deepCopy(original);
    }
}
