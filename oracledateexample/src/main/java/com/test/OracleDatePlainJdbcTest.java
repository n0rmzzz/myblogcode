package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * TODO: Run this in Oracle to create your table first:
 * <pre>
 *      CREATE TABLE DATE_TEST_TABLE(
 *          ID INT,
 *          DATE_COLUMN DATE,
 *          TIMESTAMP_COLUMN TIMESTAMP(0)
 *      );
 * </pre>
 */
public class OracleDatePlainJdbcTest
{
    private static int SERIAL = 1;
    // TODO: Modify these values according to your configuration.
    private static final String jdbcDriverCalssName = "oracle.jdbc.OracleDriver";
    private static final String jdbcUrl = "jdbc:oracle:thin:@SERVER:1521:SID";
    private static final String jdbcUsername = "user";
    private static final String jdbcPassword = "pass";

    private static synchronized int getSerial()
    {
        return SERIAL++;
    }

    private Connection conn;

    public static void main(String[] argv) throws Exception
    {
        OracleDatePlainJdbcTest oracleDateJdbcTest = new OracleDatePlainJdbcTest();
        oracleDateJdbcTest.doTest();
    }

    private void doTest() throws Exception
    {
        try
        {
            Class.forName(jdbcDriverCalssName);
            conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);

            clearValues();
            // writeValues(null);
            writeValues(Calendar.getInstance(TimeZone.getTimeZone("GMT")));

            // How do you see these values in here?
            readVelues(Calendar.getInstance(TimeZone.getTimeZone("GMT")));

            // How do you see these values in GMT?
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
            readVelues(Calendar.getInstance(TimeZone.getTimeZone("GMT")));

            // How do you see these values in NY?
            TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));
            readVelues(Calendar.getInstance(TimeZone.getTimeZone("GMT")));

            // How do you see these values in Hawaii?
            TimeZone.setDefault(TimeZone.getTimeZone("GMT-10"));
            readVelues(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
        }
    }

    private void clearValues() throws Exception
    {
        PreparedStatement insertStmt = conn.prepareStatement("DELETE FROM DATE_TEST_TABLE");
        try
        {
            insertStmt.executeUpdate();
        }
        finally
        {
            try
            {
                insertStmt.close();
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
        }
    }

    private void writeValues(Calendar cal) throws Exception
    {
        // Timestamps in milli-second UTC, 4 hours apart.
        for (long timeInMs : Arrays.asList(1369656000000L, 1369670400000L, 1369684800000L, 1369699200000L,
            1369713600000L, 1369728000000L, 1369742400000L, 1369756800000L))
        {
            Date nowDate = new Date(timeInMs);
            writeValues(nowDate, cal);
        }
    }

    private void writeValues(Date nowDate, Calendar cal) throws Exception
    {
        System.out.println("Date: " + nowDate + " (" + nowDate.getTime() + " ms since Epoch), calendar: " +
            (cal == null ? null : cal.getTimeZone().getID()));

        Timestamp nowTimestamp = new Timestamp(nowDate.getTime());
        PreparedStatement insertStmt = conn.prepareStatement(
            "INSERT INTO DATE_TEST_TABLE"
                + " (ID, DATE_COLUMN, TIMESTAMP_COLUMN)"
                + " VALUES (?, ?, ?)");
        try
        {
            insertStmt.setInt(1, getSerial());
            insertStmt.setTimestamp(2, nowTimestamp, cal);
            insertStmt.setTimestamp(3, nowTimestamp, cal);
            insertStmt.executeUpdate();
        }
        finally
        {
            try
            {
                insertStmt.close();
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
        }
    }

    private void readVelues(Calendar cal) throws SQLException
    {
        System.out.println("\nReading values with calendar: " + (cal == null ? null : cal.getTimeZone().getID()) +
            " My timezone is: " + TimeZone.getDefault().getID());
        // Read back everything in the DB
        PreparedStatement selectStmt = conn.prepareStatement(
            "SELECT ID, DATE_COLUMN, TIMESTAMP_COLUMN FROM DATE_TEST_TABLE ORDER BY ID");
        ResultSet result = null;
        try
        {
            result = selectStmt.executeQuery();
            while (result.next())
            {
                System.out.println(
                    String.format("%2s, %s, %s",
                        result.getInt(1),
                        result.getTimestamp(2, cal).toString(),
                        result.getTimestamp(3, cal).toString()
                    ));
            }
        }
        finally
        {
            try
            {
                result.close();
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
            finally
            {
                try
                {
                    selectStmt.close();
                }
                catch (Throwable t)
                {
                    t.printStackTrace();
                }
            }
        }
    }
}
