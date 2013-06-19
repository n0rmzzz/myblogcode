package com.tinywebgears.samples.railnetwork;

import java.util.HashMap;
import java.util.Map;


class StationNode
{
    private final String name;
    private final Map<String, Pair<StationNode, Integer>> nextStations;

    StationNode(String name)
    {
        assert name != null;
        this.name = name;
        this.nextStations = new HashMap<String, Pair<StationNode, Integer>>();
    }

    void addRoute(StationNode destinationNode, Integer distance)
    {
        nextStations.put(destinationNode.getName(), new Pair<StationNode, Integer>(destinationNode, distance));
    }

    String getName()
    {
        return name;
    }

    Pair<StationNode, Integer> checkNextStation(String station)
    {
        return nextStations.get(station);
    }

    Map<String, Pair<StationNode, Integer>> getAllNextStations()
    {
        return nextStations;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if ((o == null) || (o.getClass() != this.getClass()))
            return false;
        StationNode s = (StationNode) o;
        return name.equals(s.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public String toString()
    {
        return name;
    }
}
