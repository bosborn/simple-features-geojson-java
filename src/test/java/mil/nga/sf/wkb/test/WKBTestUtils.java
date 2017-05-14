package mil.nga.sf.wkb.test;

import java.io.IOException;
import java.nio.ByteOrder;

import junit.framework.TestCase;
import mil.nga.sf.CircularString;
import mil.nga.sf.CompoundCurve;
import mil.nga.sf.Curve;
import mil.nga.sf.CurvePolygon;
import mil.nga.sf.SimpleCurvePolygon;
import mil.nga.sf.Geometry;
import mil.nga.sf.SimpleGeometryCollection;
import mil.nga.sf.GeometryEnvelope;
import mil.nga.sf.GeometryType;
import mil.nga.sf.SimpleLineString;
import mil.nga.sf.SimpleLinearRing;
import mil.nga.sf.SimpleMultiLineString;
import mil.nga.sf.MultiPoint;
import mil.nga.sf.SimpleMultiPolygon;
import mil.nga.sf.Polygon;
import mil.nga.sf.SimplePoint;
import mil.nga.sf.SimplePolygon;
import mil.nga.sf.PolyhedralSurface;
import mil.nga.sf.Position;
import mil.nga.sf.TIN;
import mil.nga.sf.Triangle;
import mil.nga.sf.util.ByteReader;
import mil.nga.sf.util.ByteWriter;
import mil.nga.sf.wkb.GeometryReader;
import mil.nga.sf.wkb.GeometryWriter;
import mil.nga.sf.wkb.Utils;

/**
 * WKB test utils
 * 
 * @author osbornb
 */
public class WKBTestUtils {

	/**
	 * Compare two geometry envelopes and verify they are equal
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareEnvelopes(GeometryEnvelope expected,
			GeometryEnvelope actual) {

		if (expected == null) {
			TestCase.assertNull(actual);
		} else {
			TestCase.assertNotNull(actual);

			TestCase.assertEquals(expected.getMinX(), actual.getMinX());
			TestCase.assertEquals(expected.getMaxX(), actual.getMaxX());
			TestCase.assertEquals(expected.getMinY(), actual.getMinY());
			TestCase.assertEquals(expected.getMaxY(), actual.getMaxY());
			TestCase.assertEquals(expected.hasZ(), actual.hasZ());
			TestCase.assertEquals(expected.getMinZ(), actual.getMinZ());
			TestCase.assertEquals(expected.getMaxZ(), actual.getMaxZ());
			TestCase.assertEquals(expected.hasM(), actual.hasM());
			TestCase.assertEquals(expected.getMinM(), actual.getMinM());
			TestCase.assertEquals(expected.getMaxM(), actual.getMaxM());
		}

	}

	/**
	 * Compare two geometries and verify they are equal
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareGeometries(Geometry expected, Geometry actual) {
		if (expected == null) {
			TestCase.assertNull(actual);
		} else {
			TestCase.assertNotNull(actual);

			GeometryType geometryType = expected.getGeometryType();
			switch (geometryType) {

			case GEOMETRY:
				TestCase.fail("Unexpected Geometry Type of "
						+ geometryType.name() + " which is abstract");
			case POINT:
				comparePoint((SimplePoint) actual, (SimplePoint) expected);
				break;
			case LINESTRING:
				compareLineString((Curve) expected, (Curve) actual);
				break;
			case POLYGON:
				comparePolygon((SimplePolygon) expected, (SimplePolygon) actual);
				break;
			case MULTIPOINT:
				compareMultiPoint((MultiPoint) expected, (MultiPoint) actual);
				break;
			case MULTILINESTRING:
				compareMultiLineString((SimpleMultiLineString) expected,
						(SimpleMultiLineString) actual);
				break;
			case MULTIPOLYGON:
				compareMultiPolygon((SimpleMultiPolygon) expected,
						(SimpleMultiPolygon) actual);
				break;
			case GEOMETRYCOLLECTION:
				compareGeometryCollection((SimpleGeometryCollection<?>) expected,
						(SimpleGeometryCollection<?>) actual);
				break;
			case CIRCULARSTRING:
				compareCircularString((CircularString) expected,
						(CircularString) actual);
				break;
			case COMPOUNDCURVE:
				compareCompoundCurve((CompoundCurve) expected,
						(CompoundCurve) actual);
				break;
			case CURVEPOLYGON:
				compareCurvePolygon((SimpleCurvePolygon) expected,
						(SimpleCurvePolygon) actual);
				break;
			case MULTICURVE:
				TestCase.fail("Unexpected Geometry Type of "
						+ geometryType.name() + " which is abstract");
			case MULTISURFACE:
				TestCase.fail("Unexpected Geometry Type of "
						+ geometryType.name() + " which is abstract");
			case CURVE:
				TestCase.fail("Unexpected Geometry Type of "
						+ geometryType.name() + " which is abstract");
			case SURFACE:
				TestCase.fail("Unexpected Geometry Type of "
						+ geometryType.name() + " which is abstract");
			case POLYHEDRALSURFACE:
				comparePolyhedralSurface((PolyhedralSurface) expected,
						(PolyhedralSurface) actual);
				break;
			case TIN:
				compareTIN((TIN) expected, (TIN) actual);
				break;
			case TRIANGLE:
				compareTriangle((Triangle) expected, (Triangle) actual);
				break;
			default:
				throw new RuntimeException("Geometry Type not supported: "
						+ geometryType);
			}
		}
	}

	/**
	 * Compare to the base attributes of two geometries
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareBaseGeometryAttributes(Geometry expected,
			Geometry actual) {
		TestCase.assertEquals(expected.getGeometryType(),
				actual.getGeometryType());
		TestCase.assertEquals(expected.hasZ(), actual.hasZ());
		TestCase.assertEquals(expected.hasM(), actual.hasM());
		TestCase.assertEquals(Utils.getWkbCode(expected), Utils.getWkbCode(actual));
	}

	/**
	 * Compare the two points for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void comparePosition(Position expected, Position actual) {

		TestCase.assertEquals(expected.getX(), actual.getX());
		TestCase.assertEquals(expected.getY(), actual.getY());
		TestCase.assertEquals(expected.getZ(), actual.getZ());
		TestCase.assertEquals(expected.getM(), actual.getM());
	}

	/**
	 * Compare the two points for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void comparePoint(SimplePoint expected, SimplePoint actual) {

		compareBaseGeometryAttributes(expected, actual);
		comparePosition(expected.getPosition(), actual.getPosition());
	}

	/**
	 * Compare the two line strings for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareLineString(Curve expected, Curve actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numPositions(), actual.numPositions());
		for (int i = 0; i < expected.numPositions(); i++) {
			comparePosition(expected.getPositions().get(i), actual.getPositions().get(i));
		}
	}

	/**
	 * Compare the two polygons for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void comparePolygon(Polygon expected, Polygon actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numRings(), actual.numRings());
		for (int i = 0; i < expected.numRings(); i++) {
			compareLineString(expected.getRings().get(i), actual.getRings()
					.get(i));
		}
	}

	/**
	 * Compare the two multi points for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareMultiPoint(MultiPoint expected, MultiPoint actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numPositions(), actual.numPositions());
		for (int i = 0; i < expected.numPositions(); i++) {
			comparePosition(expected.getPositions().get(i), actual.getPositions().get(i));
		}
	}

	/**
	 * Compare the two multi line strings for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareMultiLineString(SimpleMultiLineString expected,
			SimpleMultiLineString actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numLineStrings(),
				actual.numLineStrings());
		for (int i = 0; i < expected.numLineStrings(); i++) {
			compareLineString(expected.getLineStrings().get(i), actual
					.getLineStrings().get(i));
		}
	}

	/**
	 * Compare the two multi polygons for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareMultiPolygon(SimpleMultiPolygon expected,
			SimpleMultiPolygon actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numPolygons(), actual.numPolygons());
		for (int i = 0; i < expected.numPolygons(); i++) {
			comparePolygon(expected.getPolygons().get(i), actual.getPolygons()
					.get(i));
		}
	}

	/**
	 * Compare the two geometry collections for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareGeometryCollection(
			SimpleGeometryCollection<?> expected, SimpleGeometryCollection<?> actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numGeometries(), actual.numGeometries());
		for (int i = 0; i < expected.numGeometries(); i++) {
			compareGeometries(expected.getGeometries().get(i), actual
					.getGeometries().get(i));
		}
	}

	/**
	 * Compare the two circular strings for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareCircularString(CircularString expected,
			CircularString actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numPositions(), actual.numPositions());
		for (int i = 0; i < expected.numPositions(); i++) {
			comparePosition(expected.getPositions().get(i), actual.getPositions().get(i));
		}
	}

	/**
	 * Compare the two compound curves for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareCompoundCurve(CompoundCurve expected,
			CompoundCurve actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numLineStrings(),
				actual.numLineStrings());
		for (int i = 0; i < expected.numLineStrings(); i++) {
			compareLineString(expected.getLineStrings().get(i), actual
					.getLineStrings().get(i));
		}
	}

	/**
	 * Compare the two curve polygons for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareCurvePolygon(CurvePolygon expected,
			CurvePolygon actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numRings(), actual.numRings());
		for (int i = 0; i < expected.numRings(); i++) {
			compareGeometries(expected.getRings().get(i), actual.getRings()
					.get(i));
		}
	}

	/**
	 * Compare the two polyhedral surfaces for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void comparePolyhedralSurface(PolyhedralSurface expected,
			PolyhedralSurface actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numPolygons(), actual.numPolygons());
		for (int i = 0; i < expected.numPolygons(); i++) {
			compareGeometries(expected.getPolygons().get(i), actual
					.getPolygons().get(i));
		}
	}

	/**
	 * Compare the two TINs for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareTIN(TIN expected, TIN actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numPolygons(), actual.numPolygons());
		for (int i = 0; i < expected.numPolygons(); i++) {
			compareGeometries(expected.getPolygons().get(i), actual
					.getPolygons().get(i));
		}
	}

	/**
	 * Compare the two triangles for equality
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareTriangle(Triangle expected, Triangle actual) {

		compareBaseGeometryAttributes(expected, actual);
		TestCase.assertEquals(expected.numRings(), actual.numRings());
		for (int i = 0; i < expected.numRings(); i++) {
			compareLineString(expected.getRings().get(i), actual.getRings()
					.get(i));
		}
	}

	/**
	 * Write and compare the bytes of the geometries
	 * 
	 * @param expected
	 * @param actual
	 * @throws IOException
	 */
	public static void compareGeometryBytes(Geometry expected, Geometry actual)
			throws IOException {
		compareGeometryBytes(expected, actual, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * Write and compare the bytes of the geometries using the byte order
	 * 
	 * @param expected
	 * @param actual
	 * @param byteOrder
	 * @throws IOException
	 */
	public static void compareGeometryBytes(Geometry expected, Geometry actual,
			ByteOrder byteOrder) throws IOException {

		byte[] expectedBytes = writeBytes(expected, byteOrder);
		byte[] actualBytes = writeBytes(actual, byteOrder);

		compareByteArrays(expectedBytes, actualBytes);
	}

	/**
	 * Read and compare the byte geometries
	 * 
	 * @param expected
	 * @param actual
	 * @throws IOException
	 */
	public static void compareByteGeometries(byte[] expected, byte[] actual)
			throws IOException {
		compareByteGeometries(expected, actual, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * Read and compare the byte geometries using the byte order
	 * 
	 * @param expected
	 * @param actual
	 * @param byteOrder
	 * @throws IOException
	 */
	public static void compareByteGeometries(byte[] expected, byte[] actual,
			ByteOrder byteOrder) throws IOException {

		Geometry expectedGeometry = readGeometry(expected, byteOrder);
		Geometry actualGeometry = readGeometry(actual, byteOrder);

		compareGeometries(expectedGeometry, actualGeometry);
	}

	/**
	 * Write the geometry to bytes as big endian
	 * 
	 * @param simpleGeometry
	 * @return
	 * @throws IOException
	 */
	public static byte[] writeBytes(Geometry simpleGeometry) throws IOException {
		return writeBytes(simpleGeometry, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * Write the geometry to bytes in the provided byte order
	 * 
	 * @param simpleGeometry
	 * @param byteOrder
	 * @return
	 * @throws IOException
	 */
	public static byte[] writeBytes(Geometry geometry, ByteOrder byteOrder)
			throws IOException {
		ByteWriter writer = new ByteWriter();
		writer.setByteOrder(byteOrder);
		GeometryWriter.writeGeometry(writer, geometry);
		byte[] bytes = writer.getBytes();
		writer.close();
		return bytes;
	}

	/**
	 * Read a geometry from bytes as big endian
	 * 
	 * @param bytes
	 * @return
	 */
	public static Geometry readGeometry(byte[] bytes) {
		return readGeometry(bytes, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * Read a geometry from bytes as the provided byte order
	 * 
	 * @param bytes
	 * @param byteOrder
	 * @return
	 */
	public static Geometry readGeometry(byte[] bytes, ByteOrder byteOrder) {
		ByteReader reader = new ByteReader(bytes);
		reader.setByteOrder(byteOrder);
		Geometry simpleGeometry = GeometryReader.readGeometry(reader);
		return simpleGeometry;
	}

	/**
	 * Compare two byte arrays and verify they are equal
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void compareByteArrays(byte[] expected, byte[] actual) {

		TestCase.assertEquals(expected.length, actual.length);

		for (int i = 0; i < expected.length; i++) {
			TestCase.assertEquals("Byte: " + i, expected[i], actual[i]);
		}

	}

	/**
	 * Compare two byte arrays and verify they are equal
	 * 
	 * @param expected
	 * @param actual
	 * @return true if equal
	 */
	public static boolean equalByteArrays(byte[] expected, byte[] actual) {

		boolean equal = expected.length == actual.length;

		for (int i = 0; equal && i < expected.length; i++) {
			equal = expected[i] == actual[i];
		}

		return equal;
	}

	/**
	 * Create a random position
	 * 
	 * @param hasZ
	 * @param hasM
	 * @return Position
	 */
	public static Position createPosition(boolean hasZ, boolean hasM) {

		Double x = Math.random() * 180.0 * (Math.random() < .5 ? 1 : -1);
		Double y = Math.random() * 90.0 * (Math.random() < .5 ? 1 : -1);

		Double z = hasZ ? Math.random() * 1000.0 : null;
		Double m = hasM ? Math.random() * 1000.0 : null;
		return new Position(x, y, z, m);
	}

	/**
	 * Create a random point
	 * 
	 * @param hasZ
	 * @param hasM
	 * @return
	 */
	public static SimplePoint createPoint(boolean hasZ, boolean hasM) {

		return new SimplePoint(createPosition(hasZ, hasM));
	}

	/**
	 * Create a random line string
	 * 
	 * @param hasZ
	 * @param hasM
	 * @return
	 */
	public static SimpleLineString createLineString(boolean hasZ, boolean hasM) {

		SimpleLineString lineString = new SimpleLineString(hasZ, hasM);

		int num = 2 + ((int) (Math.random() * 9));

		for (int i = 0; i < num; i++) {
			lineString.addPosition(createPosition(hasZ, hasM));
		}

		return lineString;
	}

	/**
	 * Create a random linear ring
	 * 
	 * @param hasZ
	 * @param hasM
	 * @return
	 */
	public static SimpleLinearRing createLinearRing(boolean hasZ, boolean hasM) {

		SimpleLineString lineString = new SimpleLineString(hasZ, hasM);

		int num = 3 + ((int) (Math.random() * 9));

		for (int i = 0; i < num; i++) {
			lineString.addPosition(createPosition(hasZ, hasM));
		}

		lineString.addPosition(lineString.getPositions().get(0));

		return new SimpleLinearRing(lineString);
	}

	/**
	 * Create a random polygon
	 * 
	 * @param hasZ
	 * @param hasM
	 * @return
	 */
	public static Polygon createPolygon(boolean hasZ, boolean hasM) {

		Polygon polygon = new SimplePolygon(hasZ, hasM);

		int num = 1 + ((int) (Math.random() * 5));

		for (int i = 0; i < num; i++) {
			polygon.addRing(createLinearRing(hasZ, hasM));
		}

		return polygon;
	}

	/**
	 * Create a random multi point
	 * 
	 * @param hasZ
	 * @param hasM
	 * @return
	 */
	public static MultiPoint createMultiPoint(boolean hasZ, boolean hasM) {

		MultiPoint multiPoint = new MultiPoint(hasZ, hasM);

		int num = 1 + ((int) (Math.random() * 5));

		for (int i = 0; i < num; i++) {
			multiPoint.addPosition(createPosition(hasZ, hasM));
		}

		return multiPoint;
	}

	/**
	 * Create a random multi line string
	 * 
	 * @param hasZ
	 * @param hasM
	 * @return
	 */
	public static SimpleMultiLineString createMultiLineString(boolean hasZ,
			boolean hasM) {

		SimpleMultiLineString multiLineString = new SimpleMultiLineString(hasZ, hasM);

		int num = 1 + ((int) (Math.random() * 5));

		for (int i = 0; i < num; i++) {
			multiLineString.addLineString(createLineString(hasZ, hasM));
		}

		return multiLineString;
	}

	/**
	 * Create a random multi polygon
	 * 
	 * @param hasZ
	 * @param hasM
	 * @return
	 */
	public static SimpleMultiPolygon createMultiPolygon(boolean hasZ, boolean hasM) {

		SimpleMultiPolygon multiPolygon = new SimpleMultiPolygon(hasZ, hasM);

		int num = 1 + ((int) (Math.random() * 5));

		for (int i = 0; i < num; i++) {
			multiPolygon.addPolygon(createPolygon(hasZ, hasM));
		}

		return multiPolygon;
	}

	/**
	 * Create a random geometry collection
	 * 
	 * @param hasZ
	 * @param hasM
	 * @return
	 */
	public static SimpleGeometryCollection<Geometry> createGeometryCollection(
			boolean hasZ, boolean hasM) {

		SimpleGeometryCollection<Geometry> geometryCollection = new SimpleGeometryCollection<Geometry>(
				hasZ, hasM);

		int num = 1 + ((int) (Math.random() * 5));

		for (int i = 0; i < num; i++) {

			Geometry simpleGeometry = null;
			int randomGeometry = (int) (Math.random() * 6);

			switch (randomGeometry) {
			case 0:
				simpleGeometry = createPoint(hasZ, hasM);
				break;
			case 1:
				simpleGeometry = createLineString(hasZ, hasM);
				break;
			case 2:
				simpleGeometry = createPolygon(hasZ, hasM);
				break;
			case 3:
				simpleGeometry = createMultiPoint(hasZ, hasM);
				break;
			case 4:
				simpleGeometry = createMultiLineString(hasZ, hasM);
				break;
			case 5:
				simpleGeometry = createMultiPolygon(hasZ, hasM);
				break;
			}

			geometryCollection.addGeometry(simpleGeometry);
		}

		return geometryCollection;
	}

	/**
	 * Randomly return true or false
	 * 
	 * @return
	 */
	public static boolean coinFlip() {
		return Math.random() < 0.5;
	}

}
