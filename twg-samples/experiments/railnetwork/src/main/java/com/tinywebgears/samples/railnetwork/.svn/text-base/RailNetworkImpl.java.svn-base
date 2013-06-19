package com.tinywebgears.samples.railnetwork;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinywebgears.samples.railnetwork.data.NoRouteException;
import com.tinywebgears.samples.railnetwork.data.Path;
import com.tinywebgears.samples.railnetwork.data.Route;

/**
 * RailNetworkImpl is an implementation of {@link com.tinywebgears.samples.railnetwork.RailNetwork} interface. Please
 * see the JavaDoc of the interface for more information.
 * 
 * @see com.tinywebgears.samples.railnetwork.RailNetwork
 */
class RailNetworkImpl implements RailNetwork
{
    private final static Integer DEFAULT_MAX_STOPS = Integer.MAX_VALUE;
    private final static Integer DEFAULT_DISTANCE_THRESHOLD = Integer.MAX_VALUE;

    private final Logger logger = LoggerFactory.getLogger(RailNetworkImpl.class);

    private Map<String, StationNode> stationNodes;

    public RailNetworkImpl()
    {
        stationNodes = new HashMap<String, StationNode>();
    }

    private StationNode getOrCreateStation(String station, Boolean create)
    {
        StationNode stationNode = stationNodes.get(station);
        if (stationNode != null)
            return stationNode;
        if (!create)
            return null;
        stationNode = new StationNode(station);
        stationNodes.put(station, stationNode);
        return stationNode;
    }

    @Override
    public void addRout(String source, String destination, Integer distance)
    {
        StationNode sourceNode = getOrCreateStation(source, true);
        StationNode destinationNode = getOrCreateStation(destination, true);
        sourceNode.addRoute(destinationNode, distance);
    }

    @Override
    public Route checkPath(Path path) throws NoRouteException
    {
        logger.debug("checkPath: " + path);
        Queue<String> stationsQueue = path.getStations();
        String station = stationsQueue.poll();
        StationNode stationNode = stationNodes.get(station);
        if (stationNode == null)
            throw new NoRouteException("No station " + station);
        Route route = new Route(station);
        route = checkDistance(stationNode, stationsQueue, route);
        return route;
    }

    private Route checkDistance(StationNode stationNode, Queue<String> stationsQueue, Route route)
            throws NoRouteException
    {
        String nextStation = stationsQueue.poll();
        if (nextStation == null)
            return route;
        Pair<StationNode, Integer> next = stationNode.checkNextStation(nextStation);
        if (next == null)
            throw new NoRouteException("No route from " + stationNode + " to " + nextStation);
        route = route.addStation(next.getFist().getName(), next.getSecond());
        return checkDistance(next.getFist(), stationsQueue, route);
    }

    @Override
    public Set<Route> getAllRoutes(String source, String destination, Integer minStops, Integer maxStops)
    {
        return getAllRoutes(source, destination, minStops, maxStops, null, true);
    }

    @Override
    public Set<Route> getAllRoutes(String source, String destination, Integer distanceThreshold)
    {
        return getAllRoutes(source, destination, null, null, distanceThreshold, true);
    }

    @Override
    public Route getShortestRoute(String source, String destination)
    {
        Set<Route> routes = getAllRoutes(source, destination, null, null, null, false);
        Route shortestRoute = null;
        for (Route route : routes)
        {
            logger.debug("Non-looping route: " + route);
            if (shortestRoute == null || shortestRoute.getNumberOfStops().compareTo(route.getNumberOfStops()) > 0)
                shortestRoute = route;
        }
        return shortestRoute;
    }

    private Set<Route> getAllRoutes(String source, String destination, Integer minStops, Integer maxStops,
            Integer distanceThreshold, boolean loopsAllowed)
    {
        if (minStops == null)
            minStops = 1;
        if (maxStops == null)
            maxStops = DEFAULT_MAX_STOPS;
        if (distanceThreshold == null)
            distanceThreshold = DEFAULT_DISTANCE_THRESHOLD;
        StationNode sourceNode = stationNodes.get(source);
        Queue<Pair<StationNode, Route>> nodesToProcess = new LinkedList<Pair<StationNode, Route>>();
        Route routeSoFar = new Route(sourceNode.getName());
        nodesToProcess.offer(new Pair<StationNode, Route>(sourceNode, routeSoFar));
        Set<Route> routesFound = new HashSet<Route>();
        checkRoutes(routesFound, nodesToProcess, destination, minStops, maxStops, distanceThreshold, loopsAllowed);
        return routesFound;
    }

    private void checkRoutes(Set<Route> routesFound, Queue<Pair<StationNode, Route>> nodesToProcess,
            String destination, Integer minStops, Integer maxStops, Integer distanceThreshold, Boolean loopsAllowed)
    {
        if (nodesToProcess.isEmpty())
            return;
        Pair<StationNode, Route> node = nodesToProcess.poll();
        StationNode stationNode = node.getFist();
        Route routeSoFar = node.getSecond();
        logger.debug("Station to check now: " + stationNode + " Route to the station: " + routeSoFar);
        if (routeSoFar.getNumberOfStops().compareTo(minStops) >= 0
                && routeSoFar.getNumberOfStops().compareTo(maxStops) <= 0
                && routeSoFar.getDestination().equals(destination))
        {
            logger.debug("Route found: " + routeSoFar);
            routesFound.add(routeSoFar);
        }
        if (routeSoFar.getNumberOfStops().compareTo(maxStops) > 0)
        {
            logger.debug("Maximum number of stops reached on " + routeSoFar);
            return;
        }
        for (Entry<String, Pair<StationNode, Integer>> entry : stationNode.getAllNextStations().entrySet())
        {
            logger.debug("Next station: " + entry.getKey());
            if (!loopsAllowed && routeSoFar.passesThisStation(entry.getKey()))
            {
                logger.debug("The route " + routeSoFar + " already passes from " + entry.getKey());
                continue;
            }
            Route newRoute = routeSoFar.addStation(entry.getKey(), entry.getValue().getSecond());
            if (newRoute.getTotalDistance().compareTo(distanceThreshold) >= 0)
            {
                logger.debug("The route " + newRoute + " reached/exceeded the distance threshold.");
                continue;
            }
            nodesToProcess.offer(new Pair<StationNode, Route>(entry.getValue().getFist(), newRoute));
            checkRoutes(routesFound, nodesToProcess, destination, minStops, maxStops, distanceThreshold, loopsAllowed);
        }
    }
}
