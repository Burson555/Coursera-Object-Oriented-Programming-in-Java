package roadgraph;

/**
 * MapEdge.java
 * @author Burson
 * 
 * A class to represent an Edge in the graph that represents the road map.
 */
public class MapEdge {;
	private MapNode fromNode;
	private MapNode toNode;
	private String roadName;
	private String roadType;
	private double length;
	
	public MapEdge(MapNode fromNode, MapNode toNode, String roadName,
			String roadType, double length) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}

	/**
	 * @return the source node, where the directed edge begins
	 */
	public MapNode getFromNode() {
		return this.fromNode;
	}
	
	/**
	 * @return the destination node, where the directed edge ends
	 */
	public MapNode getToNode() {
		return this.toNode;
	}
	
	/**
	 * @return the road name as String
	 */
	public String getRoadName() {
		return this.roadName;
	}
	
	/**
	 * @return the road type as String
	 */
	public String getRoadType() {
		return this.roadType;
	}
	
	/**
	 * @return the length of the road as Double
	 */
	public double getLength() {
		return this.length;
	}
	
}
