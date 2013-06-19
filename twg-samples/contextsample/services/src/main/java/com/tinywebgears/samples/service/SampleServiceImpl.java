package com.tinywebgears.samples.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleServiceImpl implements SampleService
{
    private final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

    private final String environmentName;
    private final String productName;
    private final String productVersion;
    private final String svnRevision;

    public SampleServiceImpl(String environmentName, String productName, String productVersion, String svnRevision)
    {
        this.environmentName = environmentName;
        this.productName = productName;
        this.productVersion = productVersion;
        this.svnRevision = svnRevision;
    }

    @Override
    public String getVersion()
    {
        logger.debug("SampleServiceImpl.getVersion() method called.");
        return productName + " (version: " + productVersion + ", SVN revision: " + svnRevision
                + ", deployment environment: " + environmentName + ")";
    }
}
