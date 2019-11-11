package roadgraph;

import geography.GeographicPoint;
/**
 * 
 * @author Burson
 * Do I really need this class?
 */
public class Edge {
	private GeographicPoint from;
	private GeographicPoint to;
	private String roadName;
	private String roadType;
	private double length;
	
	public Edge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) {
		this.from = from;
		this.to = to;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}
	
	public GeographicPoint getFrom() {
		return this.from;
	}
	public GeographicPoint getTo() {
		return this.to;
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
