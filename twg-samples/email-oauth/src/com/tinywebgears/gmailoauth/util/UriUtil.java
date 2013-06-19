package com.tinywebgears.gmailoauth.util;

public class UriUtil
{
    public static String getParam(String uri, String key)
    {
        String[] uriParts = uri.split("\\?");
        if (uriParts.length < 2)
            return null;
        String[] params = uriParts[1].split("&");
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            if (name.equals(key))
                return value;
        }
        return null;
    }
}
