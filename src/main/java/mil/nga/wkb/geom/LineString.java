package mil.nga.wkb.geom;

import java.util.ArrayList;
import java.util.List;

/**
 * A Curve that connects two or more points in space.
 * 
 * @author osbornb
 */
public class LineString extends Curve {

	/**
	 * List of points
	 */
	private List<Point> points = new ArrayList<Point>();

	/**
	 * Constructor
	 * 
	 * @param hasZ
	 * @param hasM
	 */
	public LineString(boolean hasZ, boolean hasM) {
		super(GeometryType.LINESTRING, hasZ, hasM);
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 * @param hasZ
	 * @param hasM
	 */
	protected LineString(GeometryType type, boolean hasZ, boolean hasM) {
		super(type, hasZ, hasM);
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public void addPoint(Point point) {
		points.add(point);
	}

	/**
	 * Get the number of points
	 * 
	 * @return
	 */
	public int numPoints() {
		return points.size();
	}

}
