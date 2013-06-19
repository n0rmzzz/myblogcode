package com.tinywebgears.gaedownload.core.service;

public class ServiceException extends RuntimeException
{
    public ServiceException(String userMessage)
    {
        super(userMessage);
    }

    public ServiceException(String userMessage, Throwable cause)
    {
        super(userMessage, cause);
    }
}
