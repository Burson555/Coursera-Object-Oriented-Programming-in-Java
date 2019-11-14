/**
 * @author UCSD MOOC development team and Burson
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between roads
 *
 */
package roadgraph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

public class MapGraph {
	private int numVertices;
	private int numEdges;
	private HashMap<geography.GeographicPoint, MapNode> mapGraph;
	private HashMap<MapNode, HashMap<MapNode, MapEdge>> edges;
	private HashMap<MapNode, Double> costMap;
	private final Double MAX_COST = 9999.9999;
	private final Double MIN_COST = 0.0;
	
	/**
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		this.numVertices = 0;
		this.numEdges = 0;
		this.mapGraph = new HashMap<geography.GeographicPoint, MapNode>();
		this.edges = new HashMap<MapNode, HashMap<MapNode, MapEdge>>();
		this.costMap = new HashMap<MapNode, Double>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return this.numVertices;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return this.numEdges;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return this.mapGraph.keySet();
	}
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if (location == null || this.isVertex(location))
			return false;
		this.mapGraph.put(location, new MapNode(location, this.costMap));
		this.numVertices++;
		this.costMap.put(this.mapGraph.get(location), this.MAX_COST);
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in KM
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		this.validateParams(from, to, roadName, roadType, length);
		
		MapNode fromNode = this.mapGraph.get(from);
		MapNode toNode = this.mapGraph.get(to);
		fromNode.addNeighbor(toNode);
		
		if (!this.edges.containsKey(fromNode))
			this.edges.put(fromNode, new HashMap<MapNode, MapEdge>());
		HashMap<MapNode, MapEdge> edgeMap = this.edges.get(fromNode);
		MapEdge edge = new MapEdge(fromNode, toNode, roadName, roadType, length);
		edgeMap.put(toNode, edge);
		
		this.numEdges++;
	}
	
	/**
	 * Validate the values of inputs of function .addEdge()
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	private void validateParams(GeographicPoint from, GeographicPoint to, String roadName, String roadType,
			double length) throws IllegalArgumentException {
		if (length < 0)
			throw new IllegalArgumentException();
		if (from == null || to == null || roadName == null || roadType == null)
			throw new IllegalArgumentException();
		if (!(this.isVertex(from) && this.isVertex(to)))
			throw new IllegalArgumentException();
	}

	/**
	 * Checks whether a vertex exists
	 */
	private boolean isVertex(GeographicPoint v) {
		return this.mapGraph.containsKey(v);
	}

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		
		boolean found = this.implementBFS(start, goal, nodeSearched, parentMap);
		if (!found)
			return new LinkedList<GeographicPoint>();

		List<GeographicPoint> retList = this.createRetList(goal, parentMap);
		Collections.reverse(retList);
		return retList;
	}
	
	/** Breadth First Search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @param parentMap The MapNode to parent MapNode map used for path construction
	 *   path from start to goal (including both start and goal).
	 */
	private boolean implementBFS(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched, HashMap<MapNode, MapNode> parentMap) {
		// Initialization
		MapNode startNode = this.mapGraph.get(start);
		HashSet<MapNode> visited = new HashSet<MapNode>();
		List<MapNode> queue = new LinkedList<MapNode>();
		queue.add(startNode);
		visited.add(startNode);
		
		// Search for the goal
		while (!queue.isEmpty()) {
			MapNode curr = queue.remove(0);
			// Hook for visualization.
			nodeSearched.accept(curr.getLocation());
			if (curr.getLocation().equals(goal))
				return true;
			
			List<MapNode> neighbors = curr.getNeighbors();
			for (MapNode temp : neighbors) {
				if (!visited.contains(temp)) {
					visited.add(temp);
					queue.add(temp);
					parentMap.put(temp, curr);
				}
			}
		}
		
		return false;
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		
		boolean found = this.implementSearch(start, goal, nodeSearched, parentMap, true);
		if (!found)
			return new LinkedList<GeographicPoint>();

		List<GeographicPoint> retList = this.createRetList(goal, parentMap);
		Collections.reverse(retList);
		return retList;		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		
		boolean found = this.implementSearch(start, goal, nodeSearched, parentMap, false);
		if (!found)
			return new LinkedList<GeographicPoint>();

		List<GeographicPoint> retList = this.createRetList(goal, parentMap);
		Collections.reverse(retList);
		return retList;	
	}	
	
	/** Create the list to return
	 * 
	 * @param goal the destination we are planing to reach
	 * @param parentMap parentMap The MapNode to parent MapNode map used for path construction
	 * @param retList The list of intersections that form the shortest path
	 */
	private List<GeographicPoint> createRetList(GeographicPoint goal, HashMap<MapNode, MapNode> parentMap) {
		List<GeographicPoint> retList = new LinkedList<GeographicPoint>();
		MapNode goalNode = this.mapGraph.get(goal);
		retList.add(goal);
		while (parentMap.containsKey(goalNode)) {
			MapNode parent = parentMap.get(goalNode);
			retList.add(parent.getLocation());
			goalNode = parent;
		}
		return retList;
	}

	/** Search Algorithm for both Dijkstra's and A*
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @param parentMap The MapNode to parent MapNode map used for path construction
	 *   path from start to goal (including both start and goal).
	 * @param isDijkstra true if we are running Dijkstra's algorithms, false for A* Search
	 * @return whether or not we've found the path from start to goal (including both start and goal).
	 */
	private boolean implementSearch(GeographicPoint start, GeographicPoint goal, 
			Consumer<GeographicPoint> nodeSearched, HashMap<MapNode, MapNode> parentMap, boolean isDijkstra) {
		// Initialization
		MapNode startNode = this.mapGraph.get(start);
		HashSet<MapNode> visited = new HashSet<MapNode>();
		PriorityQueue<MapNode> queue = new PriorityQueue<MapNode>();
		queue.add(startNode);
		this.costMap.put(startNode, this.MIN_COST);
		
		// Search for the goal
		while (!queue.isEmpty()) {
			MapNode curr = queue.poll();
			if (curr.getLocation().equals(goal))
				return true;

			if (visited.add(curr))
				// Hook for visualization.
				nodeSearched.accept(curr.getLocation());
			else
				continue;
			List<MapNode> neighbors = curr.getNeighbors();
			for (MapNode temp : neighbors) {
				if (isDijkstra)
					this.updateCost(parentMap, curr, temp);
				else
					this.updateCost(parentMap, curr, temp, goal);
				queue.add(temp);
				// if the line above is deleted
				// duplicates will be processed several times
				// and result in the failure of the program 
			}
		}
		
		return false;
	}

	/** Cost updating function for Dijkstra's Algorithm
	 * @param curr the start vertex of the directed edge
	 * @param temp the end vertex of the directed edge
	 */
	private void updateCost(HashMap<MapNode, MapNode> parentMap, MapNode curr, MapNode temp) {
		Double new_cost = this.costMap.get(curr) + this.getEdgeLength(curr, temp);
		if (new_cost < this.costMap.get(temp)) {
			this.costMap.put(temp, new_cost);
			parentMap.put(temp, curr);
		}
	}

	/** Cost updating function for A* Search
	 * @param curr the start vertex of the directed edge
	 * @param temp the end vertex of the directed edge
	 */
	private void updateCost(HashMap<MapNode, MapNode> parentMap, MapNode curr, MapNode temp, GeographicPoint goal) {
		Double new_cost = this.costMap.get(curr) + this.getEdgeLength(curr, temp) + temp.getLocation().distance(goal);
		if (new_cost < this.costMap.get(temp)) {
			this.costMap.put(temp, new_cost);
			parentMap.put(temp, curr);
		}
	}

	/** Get the edge length of edge from curr to edge
	 * @param curr the start vertex of the directed edge
	 * @param temp the end vertex of the directed edge
	 */
	private Double getEdgeLength(MapNode curr, MapNode temp) {
		return this.edges.get(curr).get(temp).getLength();
	}
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}