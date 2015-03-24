package mil.nga.giat.wkb.geom;

/**
 * Triangle
 * 
 * @author osbornb
 */
public class Triangle extends Polygon {

	/**
	 * Constructor
	 * 
	 * @param hasZ
	 * @param hasM
	 */
	public Triangle(boolean hasZ, boolean hasM) {
		super(GeometryType.TRIANGLE, hasZ, hasM);
	}

}
