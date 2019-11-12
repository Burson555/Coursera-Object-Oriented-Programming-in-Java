package roadgraph;

import geography.GeographicPoint;

/**
 * 
 * @author Burson
 * Do I really need this class?
 */
public class MapEdge {
	private GeographicPoint endNode;
	private String roadName;
	private String roadType;
	private double length;
	
	public MapEdge(GeographicPoint endNode, String roadName,
			String roadType, double length) {
		this.endNode = new GeographicPoint(endNode.getX(), endNode.getY());
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}

	public GeographicPoint getTo() {
		return this.endNode;
	}
	public String getRoadName() {
		return this.roadName;
	}
	public String getRoadType() {
		return this.roadType;
	}
	public double getLength() {
		return this.length;
	}
	
}
