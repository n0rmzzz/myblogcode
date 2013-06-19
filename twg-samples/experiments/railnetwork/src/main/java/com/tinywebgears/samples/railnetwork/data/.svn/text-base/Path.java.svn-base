package com.tinywebgears.samples.railnetwork.data;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Immutable object representing a path amongst stations.
 */
public class Path
{
    private final LinkedList<String> stations;

    public Path()
    {
        stations = new LinkedList<String>();
    }

    private Path(LinkedList<String> stations)
    {
        this.stations = stations;
    }

    public static Path parseString(String pathString)
    {
        Path path = new Path();
        String[] stations = pathString.split("-");
        for (String station : stations)
            path = path.addStation(station);
        return path;
    }

    public Path addStation(String station)
    {
        LinkedList<String> newStations = new LinkedList<String>();
        newStations.addAll(stations);
        newStations.offer(station);
        return new Path(newStations);
    }

    public Queue<String> getStations()
    {
        Queue<String> copy = new LinkedList<String>();
        copy.addAll(stations);
        return copy;
    }

    public String getSource()
    {
        return stations.getFirst();
    }

    public String getDestination()
    {
        return stations.getLast();
    }

    boolean passesThisStation(String testStation)
    {
        boolean first = true;
        for (String station : stations)
        {
            if (first)
            {
                first = false;
                continue;
            }
            if (station.equals(testStation))
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if ((o == null) || (o.getClass() != this.getClass()))
            return false;
        Path p = (Path) o;
        return toString().equals(p.toString());
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }

    @Override
    public String toString()
    {
        StringBuilder pathString = new StringBuilder();
        Boolean first = true;
        for (String station : stations)
        {
            if (first)
                first = false;
            else
                pathString.append("-");
            pathString.append(station);
        }
        return pathString.toString();
    }
}
