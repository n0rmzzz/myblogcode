package com.tinywebgears.gaedownload.servlet;

import java.io.IOException;
import java.security.Principal;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinywebgears.gaedownload.core.dao.PMFHolder;
import com.tinywebgears.gaedownload.core.model.MyFile;

public class TextFileServlet extends HttpServlet
{
    public static final String PARAM_BLOB_ID = "id";
    public static final String SERVLET_PATH = "/gaedownload/servefile";

    private final Logger logger = LoggerFactory.getLogger(TextFileServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        Principal userPrincipal = req.getUserPrincipal();
        PersistenceManager pm = PMFHolder.get().getPersistenceManager();
        Long id = Long.parseLong(req.getParameter(PARAM_BLOB_ID));
        MyFile myfile = pm.getObjectById(MyFile.class, id);

        if (!userPrincipal.getName().equals(myfile.getUserName()))
        {
            logger.info("TextFileServlet.doGet - current user: " + userPrincipal + " file owner: "
                    + myfile.getUserName());
            return;
        }

        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=\"" + myfile.getName() + "\"");
        res.getOutputStream().write(myfile.getFile().getBytes());
    }
}
