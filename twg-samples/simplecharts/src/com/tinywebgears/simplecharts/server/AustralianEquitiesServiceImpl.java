package com.tinywebgears.simplecharts.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tinywebgears.simplecharts.client.AustralianEquitiesService;
import com.tinywebgears.simplecharts.shared.FieldVerifier;
import com.tinywebgears.simplecharts.shared.StringHelper;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AustralianEquitiesServiceImpl extends RemoteServiceServlet implements AustralianEquitiesService
{
    private final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public Map<Date, Map<String, Double>> getPriceInfo(String... stockCodes) throws IllegalArgumentException
    {
        // Verify that the input is valid.
        if (!FieldVerifier.areValidSecurityCodes(stockCodes))
        {
            // If the input is not valid, throw an IllegalArgumentException back to the client.
            throw new IllegalArgumentException(
                    "Stock code must be at least 3 characters long. Stock codes can be separated by a comma.");
        }

        // Simple date formatter to parse the dates
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        // Price data per date
        Map<Date, Map<String, Double>> resultMap = new HashMap<Date, Map<String, Double>>();
        // Which security code is in which column
        Map<String, Integer> indexMap = new HashMap<String, Integer>();

        try
        {
            // BufferedReader br = new BufferedReader(new FileReader("/price-data.csv"));
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(
                    "/price-data.csv")));

            String line = br.readLine();
            if (line == null)
            {
                System.out.println("Invalid file, header empty.");
                return resultMap;
            }
            String[] headerTokens = line.split(",");
            if (headerTokens.length < 2)
            {
                System.out.println("Invalid file, header too short.");
                return resultMap;
            }
            for (Integer index = 1; index < headerTokens.length; index++)
            {
                String codeInHeader = StringHelper.unquote(headerTokens[index]).trim().toUpperCase();
                indexMap.put(codeInHeader, index);
                System.out.println("" + codeInHeader + " is at column " + index);
            }
            List<String> filteredCodes = new ArrayList<String>();
            for (String stockCode : stockCodes)
            {
                stockCode = stockCode.toUpperCase();
                if (indexMap.get(stockCode) != null)
                {
                    System.out.println("We have " + stockCode);
                    filteredCodes.add(stockCode);
                }
                else
                    System.out.println("We don't have any data about " + stockCode);
            }

            while ((line = br.readLine()) != null)
            {
                String[] tokens = line.split(",");
                if (tokens.length < 2)
                    continue;
                String dateString = StringHelper.unquote(tokens[0]).trim();
                Date date = df.parse(dateString);
                Map<String, Double> valuesMap = new HashMap<String, Double>();
                resultMap.put(date, valuesMap);
                for (String stockCode : filteredCodes)
                {
                    Integer index = indexMap.get(stockCode);
                    try
                    {
                        if (tokens.length > index)
                        {
                            Double price = Double.valueOf(tokens[index].trim());
                            valuesMap.put(stockCode, price);
                        }
                        else
                        {
                            valuesMap.put(stockCode, null);
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        valuesMap.put(stockCode, null);
                    }
                }
            }

            br.close();

            for (Entry<Date, Map<String, Double>> entry : resultMap.entrySet())
            {
                System.out.print(entry.getKey() + " - ");
                for (Entry<String, Double> valueEntry : entry.getValue().entrySet())
                    System.out.print(valueEntry.getKey() + ":" + valueEntry.getValue() + " ");
                System.out.println();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return resultMap;
    }
}
