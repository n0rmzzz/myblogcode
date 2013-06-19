package com.tinywebgears.samples.xmlrpc;

import java.util.HashMap;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory.StatelessProcessorFactoryFactory;

public class ConfiguredBeanProcessorFactory extends StatelessProcessorFactoryFactory
{
    private HashMap classBeanMap = new HashMap();

    protected Object getRequestProcessor(Class cls) throws XmlRpcException
    {
        Object o = getClassBeanMap().get(cls);
        if (o == null)
            o = getClassBeanMap().get(cls.getName());
        if (o == null)
            throw new XmlRpcException("Handler bean not found for: " + cls);
        return o;
    }

    public HashMap getClassBeanMap()
    {
        return classBeanMap;
    }

    public void setClassBeanMap(HashMap classBeanMap)
    {
        this.classBeanMap = classBeanMap;
    }
}
