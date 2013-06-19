package com.tinywebgears.samples.railnetwork;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinywebgears.samples.railnetwork.data.NoRouteException;
import com.tinywebgears.samples.railnetwork.data.Route;

public class NetworkPlannerTest
{
    private final static String TEST_GRAPH = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
    private static NetworkPlanner planner;

    private final Logger logger = LoggerFactory.getLogger(NetworkPlannerTest.class);

    @BeforeClass
    public static void oneTimeSetUp()
    {
        planner = new NetworkPlannerImpl(TEST_GRAPH);
    }

    @Test
    public void testTrainPlannerIsInitialized()
    {
        Assert.assertEquals(true, planner.isInitialized());
    }

    @Test
    public void testFollowRoute_No1() throws NoRouteException
    {
        Route route = planner.checkPath("A-B-C");
        Assert.assertNotNull(route);
        Assert.assertEquals(Integer.valueOf(9), route.getTotalDistance());
        logger.info("Output #1: " + route.getTotalDistance());
    }

    @Test
    public void testFollowRoute_No2() throws NoRouteException
    {
        Route route = planner.checkPath("A-D");
        Assert.assertNotNull(route);
        Assert.assertEquals(Integer.valueOf(5), route.getTotalDistance());
        logger.info("Output #2: " + route.getTotalDistance());
    }

    @Test
    public void testFollowRoute_No3() throws NoRouteException
    {
        Route route = planner.checkPath("A-D-C");
        Assert.assertNotNull(route);
        Assert.assertEquals(Integer.valueOf(13), route.getTotalDistance());
        logger.info("Output #3: " + route.getTotalDistance());
    }

    @Test
    public void testFollowRoute_No4() throws NoRouteException
    {
        Route route = planner.checkPath("A-E-B-C-D");
        Assert.assertNotNull(route);
        Assert.assertEquals(Integer.valueOf(22), route.getTotalDistance());
        logger.info("Output #4: " + route.getTotalDistance());
    }

    @Test(expected = NoRouteException.class)
    public void testFollowRoute_No5() throws NoRouteException
    {
        try
        {
            planner.checkPath("A-E-D");
        }
        catch (NoRouteException e)
        {
            logger.info("Output #5: NO SUCH ROUTE");
            throw e;
        }
        Assert.assertTrue(false);
    }

    @Test
    public void testGetRoutesLimitedStops_No6() throws NoRouteException
    {
        Set<Route> allRoutes = planner.getAllRoutes("C", "C", 1, 3);
        Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(allRoutes.size()));
        Set<String> expectedRoutes = new HashSet<String>(Arrays.asList("C-D-C", "C-E-B-C"));
        logger.info("Output #6: " + allRoutes.size());
        for (Route route : allRoutes)
        {
            Assert.assertTrue(expectedRoutes.contains(route.getPath().toString()));
            logger.debug("Output #6: " + route);
        }
    }

    @Test
    public void testGetRoutesLimitedStops_No7() throws NoRouteException
    {
        Set<Route> allRoutes = planner.getAllRoutes("A", "C", 4, 4);
        Assert.assertEquals(Integer.valueOf(3), Integer.valueOf(allRoutes.size()));
        Set<String> expectedRoutes = new HashSet<String>(Arrays.asList("A-B-C-D-C", "A-D-C-D-C", "A-D-E-B-C"));
        logger.info("Output #7: " + allRoutes.size());
        for (Route route : allRoutes)
        {
            Assert.assertTrue(expectedRoutes.contains(route.getPath().toString()));
            logger.debug("Output #7: " + route);
        }
    }

    @Test
    public void testGetShortestRoute_No8() throws NoRouteException
    {
        Route route = planner.getShortestRoute("A", "C");
        Assert.assertEquals(Integer.valueOf(9), route.getTotalDistance());
        logger.info("Output #8: " + route.getTotalDistance());
    }

    @Test
    public void testGetShortestRoute_No9() throws NoRouteException
    {
        Route route = planner.getShortestRoute("B", "B");
        Assert.assertEquals(Integer.valueOf(9), route.getTotalDistance());
        logger.info("Output #9: " + route.getTotalDistance());
    }

    @Test
    public void testGetRoutesMaxDistance_No9() throws NoRouteException
    {
        Set<Route> allRoutes = planner.getAllRoutes("C", "C", 30);
        logger.info("Output #10: " + allRoutes.size());
        for (Route route : allRoutes)
            logger.debug("Output #10: " + route);
    }
}
