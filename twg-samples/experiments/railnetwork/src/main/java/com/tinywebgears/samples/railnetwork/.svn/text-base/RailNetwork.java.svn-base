package com.tinywebgears.samples.railnetwork;

import java.util.Set;

import com.tinywebgears.samples.railnetwork.data.NoRouteException;
import com.tinywebgears.samples.railnetwork.data.Path;
import com.tinywebgears.samples.railnetwork.data.Route;

/**
 * RailNetwork manages a rail network and provides information about the available routes.
 */
interface RailNetwork
{
    void addRout(String source, String destination, Integer distance);

    Route checkPath(Path path) throws NoRouteException;

    Set<Route> getAllRoutes(String source, String destination, Integer minStops, Integer maxStops);

    Set<Route> getAllRoutes(String source, String destination, Integer distanceThreshold);

    Route getShortestRoute(String source, String destination);
}
