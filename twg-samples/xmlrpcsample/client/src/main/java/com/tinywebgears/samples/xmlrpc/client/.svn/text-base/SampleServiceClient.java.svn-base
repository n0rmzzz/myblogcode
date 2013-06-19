package com.tinywebgears.samples.xmlrpc.client;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

public class SampleServiceClient
{
    public static void main(String args[]) throws MalformedURLException, XmlRpcException
    {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc-backend-services/xmlrpc/"));

        XmlRpcClient client = new XmlRpcClient();
        client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
        client.setConfig(config);

        Object response = client.execute("sampleService.getVersion", new Object[0]);
        System.out.println("Response: " + response);
    }
}
