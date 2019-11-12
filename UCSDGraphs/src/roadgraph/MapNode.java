package roadgraph;

import java.util.LinkedList;
import java.util.List;

/**
 * MapNode.java
 * @author Burson
 * 
 * A class to represent a Node in the graph that represents the road map.
 */
public class MapNode {
	
	private List<MapNode> neighbors;
	private geography.GeographicPoint location;
	
	/**
	 * Create a new empty MapNode with the given location and an empty neighbor list
	 * @param GeographicPoint the GeographicPoint object that stores location
	 */
	public MapNode(geography.GeographicPoint location)
	{
		this.location = location;
		neighbors = new LinkedList<MapNode>();
	}

	/**
	 * @param neighbor ONE neighbor MapNode to add
	 */
	public void addNeighbor(MapNode neighbor) 
	{
		neighbors.add(neighbor);
	}
	
	/**
	 * @return the neighbors
	 */
	public List<MapNode> getNeighbors() {
		return neighbors;
	}

	/**
	 * @return the location represented as class GeographicPoint
	 */
	public geography.GeographicPoint getLocation() {
		return this.location;
	}

	/**
	 * @return the latitude
	 */
	public double getLat() {
		return this.location.getX();
	}

	/**
	 * @return the longitude
	 */
	public double getLon() {
		return this.location.getY();
	}
	
	
}
