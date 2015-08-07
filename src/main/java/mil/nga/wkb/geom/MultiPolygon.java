package mil.nga.wkb.geom;

import java.util.List;

/**
 * A restricted form of MultiSurface where each Surface in the collection must
 * be of type Polygon.
 * 
 * @author osbornb
 */
public class MultiPolygon extends MultiSurface<Polygon> {

	/**
	 * Constructor
	 * 
	 * @param hasZ
	 * @param hasM
	 */
	public MultiPolygon(boolean hasZ, boolean hasM) {
		super(GeometryType.MULTIPOLYGON, hasZ, hasM);
	}

	/**
	 * Get the polygons
	 * 
	 * @return
	 */
	public List<Polygon> getPolygons() {
		return getGeometries();
	}

	/**
	 * Set the polygons
	 * 
	 * @param polygons
	 */
	public void setPolygons(List<Polygon> polygons) {
		setGeometries(polygons);
	}

	/**
	 * Add a polygon
	 * 
	 * @param polygon
	 */
	public void addPolygon(Polygon polygon) {
		addGeometry(polygon);
	}

	/**
	 * Get the number of polygons
	 * 
	 * @return
	 */
	public int numPolygons() {
		return numGeometries();
	}

}
