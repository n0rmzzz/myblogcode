package com.tinywebgears.samples.xmlrpc;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

// @Controller
// @RequestMapping("/xmlrpc/*")
public class XmlRpcServerController extends AbstractController implements InitializingBean
{
    private XmlRpcServletServer server = new XmlRpcServletServer();
    private PropertiesHandlerMapping mapping = new PropertiesHandlerMapping();
    private RequestProcessorFactoryFactory factory;
    private Properties mappings;

    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        server.execute(req, resp);
        return null;
    }

    public void afterPropertiesSet() throws Exception
    {
        System.out.println("************* afterPropertiesSet - mappings: " + mappings);
        XmlRpcServerConfigImpl cfg = (XmlRpcServerConfigImpl) server.getConfig();
        cfg.setEnabledForExtensions(true);
        mapping.setRequestProcessorFactoryFactory(getFactory());
        mapping.load(mappings);
        server.setHandlerMapping(mapping);
    }

    public RequestProcessorFactoryFactory getFactory()
    {
        return factory;
    }

    public void setFactory(RequestProcessorFactoryFactory factory)
    {
        this.factory = factory;
    }

    public Properties getMappings()
    {
        return mappings;
    }

    public void setMappings(Properties mappings)
    {
        System.out.println("************* setMappings: " + mappings);
        this.mappings = mappings;
    }
}
