Design Notes:
There is a RailNetwork interface (and its default implementation RailNetworkImpl) that holds the details of a rail network and provides information about the available routes.
Then there is a NetworkPlanner interface (and its default implementation NetworkPlannerImpl) which is a facade on RailNetwork and provides the ability to integrate more functionalities (such as pricing) into the application later.
The rail network itself is kept as a graph of nodes as defined in the StationNode class and a mapping between station names and graph nodes is maintained in the RailNetwork for quick access to the stations.
In order to verify a route (tests 1-5) the source station's node is looked up and all the links are followed until the route is complete or not found.
In order to find routes between stations (tests 6-10) the source station's node is looked up and the graph is searched breadth first checking the appropriate criteria in every case.

How to run this program:
Install JDK (1.6.x) and Maven (version 2.x). Run 'mvn clean test' and check the output.
Alternatively import the project in Eclipse and run its JUnit tests in src/test/java.

