package com.tinywebgears.samples.railnetwork.data;

/**
 * Exception raised on different occasions when a route can not be found.
 */
@SuppressWarnings("serial")
public class NoRouteException extends Exception
{
    public NoRouteException(String message)
    {
        super(message);
    }

    public NoRouteException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
