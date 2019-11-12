package roadgraph;

import java.util.LinkedList;
import java.util.List;

import geography.GeographicPoint;

/**
 * MapNode.java
 * 
 * A class to represent a Node in a graph which is a Map of road map with road intersections as vertices.
 */
public class MapNode {
	
	private List<MapNode> neighbors;
	private char displayChar;
	private geography.GeographicPoint location;
	
	public static final char EMPTY = '-';
	public static final char PATH = '*';
	public static final char FROM = 'F';
	public static final char TO = 'T';
	
	/**
	 * Create a new empty MapNode with the given geographic info of GeographicPoint gp and an empty neighbor list
	 * @param GeographicPoint the GeographicPoint object that stores the coordinate of the wanted position
	 */
	public MapNode(geography.GeographicPoint location)
	{
		this.location = new geography.GeographicPoint(location.getX(), location.getY());
		neighbors = new LinkedList<MapNode>();
		displayChar = EMPTY;
	}

	/**
	 * @return the displayChar
	 */
	public char getDisplayChar() {
		return displayChar;
	}

	/**
	 * @param displayChar the displayChar to set
	 */
	public void setDisplayChar(char displayChar) {
		this.displayChar = displayChar;
	}

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
