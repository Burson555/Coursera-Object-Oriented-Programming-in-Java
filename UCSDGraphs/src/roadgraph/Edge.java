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
}
