package com.tinywebgears.samples.xmlrpc;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

public class PropertiesHandlerMapping extends PropertyHandlerMapping
{
    public void load(Properties props) throws XmlRpcException
    {
        for (Iterator i = props.entrySet().iterator(); i.hasNext();)
        {
            Map.Entry entry = (Map.Entry) i.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            Class c = newHandlerClass(getClass().getClassLoader(), value);
            registerPublicMethods(key, c);
        }
    }
}
