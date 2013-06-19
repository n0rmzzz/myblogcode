package com.tinywebgears.simplecharts.client;

import java.util.Date;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>AustralianEquitiesService</code>.
 */
public interface AustralianEquitiesServiceAsync
{
    void getPriceInfo(String[] securityCodes, AsyncCallback<Map<Date, Map<String, Double>>> callback)
            throws IllegalArgumentException;
}
