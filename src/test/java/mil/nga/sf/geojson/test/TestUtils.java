package mil.nga.sf.geojson.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import mil.nga.sf.LineString;
import mil.nga.sf.LinearRing;
import mil.nga.sf.geojson.GeoJsonObjectFactory;
import mil.nga.sf.geojson.Geometry;
import mil.nga.sf.geojson.Point;
import mil.nga.sf.geojson.Position;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
	
	private static double EPSILON = 0.00001d;
	
	private static ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static void assertPoint(Double expectedLongitude, Double expectedLatitude, Double expectedAltitude,
									   Point point) {
		assertPoint(expectedLongitude, expectedLatitude, expectedAltitude, new ArrayList<Double>(), point);
	}

	public static void assertPosition(Double expectedLongitude, Double expectedLatitude, Double expectedAltitude,
			   List<Double> expectedAdditionalElements, Position position) {
		TestCase.assertEquals(expectedLongitude, position.getX(), EPSILON);
		TestCase.assertEquals(expectedLatitude, position.getY(), EPSILON);
		if(expectedAltitude == null) {
			TestCase.assertNull(position.getZ());
		} else {
			TestCase.assertEquals(expectedAltitude, position.getZ(), EPSILON);
			if(expectedAdditionalElements == null){
				final List<Double> ae = ((Position)position).getAdditionalElements();
				TestCase.assertTrue((ae == null) || ae.isEmpty());
			} else {
				TestCase.assertTrue(expectedAdditionalElements.equals(((Position)position).getAdditionalElements()));
			}
		}
	}
	
	public static void assertPosition(Double expectedLongitude, Double expectedLatitude, Double expectedAltitude,
									   List<Double> expectedAdditionalElements, mil.nga.sf.Point position) {
		TestCase.assertEquals(expectedLongitude, position.getX(), EPSILON);
		TestCase.assertEquals(expectedLatitude, position.getY(), EPSILON);
		if(expectedAltitude == null) {
			TestCase.assertNull(position.getZ());
		} else {
			TestCase.assertEquals(expectedAltitude, position.getZ(), EPSILON);
			if(expectedAdditionalElements == null){
				TestCase.assertNull(position.getM());
			} else {
				TestCase.assertEquals(expectedAdditionalElements.size(), 1);
				TestCase.assertEquals(expectedAdditionalElements.get(0), position.getM(), EPSILON);
			}
		}
	}

	public static mil.nga.sf.MultiPolygon getMultiPolygonWithRings() {
		List<mil.nga.sf.Polygon> polygons = new ArrayList<mil.nga.sf.Polygon>();
		List<LineString> rings = new ArrayList<>();
		List<mil.nga.sf.Point> points = new ArrayList<mil.nga.sf.Point>();
		points.add(new mil.nga.sf.Point(-100d, -50d));
		points.add(new mil.nga.sf.Point( 100d, -50d));
		points.add(new mil.nga.sf.Point(   1d,  50d));
		LinearRing ring = new LinearRing(points);
		rings.add(ring);points = new ArrayList<mil.nga.sf.Point>();
		points.add(new mil.nga.sf.Point(-50d, -25d));
		points.add(new mil.nga.sf.Point( 50d, -25d));
		points.add(new mil.nga.sf.Point( -1d,  25d));
		ring = new LinearRing(points);
		rings.add(ring);
		mil.nga.sf.Polygon polygon = new mil.nga.sf.Polygon(rings);
		polygons.add(polygon);
		mil.nga.sf.MultiPolygon multiPolygon = new mil.nga.sf.MultiPolygon(polygons);
		return multiPolygon;
	}
	public static void assertPoint(Double expectedLongitude, Double expectedLatitude, Double expectedAltitude,
									   List<Double> expectedAdditionalElements, Point point) {
		assertPosition(expectedLongitude, expectedLatitude, expectedAltitude, expectedAdditionalElements, point.getCoordinates());
	}
	
	public static void compareAsNodes(Object obj, String input) throws JsonProcessingException, IOException{
		Geometry geometry = GeoJsonObjectFactory.createObject(obj);
		JsonNode nodeFromPojo = mapper.valueToTree(geometry);
		JsonNode nodeFromString = mapper.readTree(input);
		TestCase.assertEquals(nodeFromPojo, nodeFromString);
	}
	
	public static void toMap(mil.nga.sf.Geometry simpleGeometry) {
		
		Geometry geometry = GeoJsonObjectFactory.createObject(simpleGeometry);
		TestCase.assertNotNull(geometry);

		Map<String, Object> map = GeoJsonObjectFactory.toMap(simpleGeometry);
		TestCase.assertNotNull(map);
		TestCase.assertFalse(map.isEmpty());

		Geometry geometryFromMap = GeoJsonObjectFactory.toGeometry(map);
		TestCase.assertNotNull(geometryFromMap);

		TestCase.assertEquals(geometry.getGeometry(),
				geometryFromMap.getGeometry());

	}

	public static void toStringValue(mil.nga.sf.Geometry simpleGeometry) {

		Geometry geometry = GeoJsonObjectFactory.createObject(simpleGeometry);
		TestCase.assertNotNull(geometry);

		String stringValue = GeoJsonObjectFactory.toStringValue(simpleGeometry);
		TestCase.assertNotNull(stringValue);
		TestCase.assertFalse(stringValue.isEmpty());

		Geometry geomteryFromString = GeoJsonObjectFactory
				.toGeometry(stringValue);
		TestCase.assertNotNull(geomteryFromString);

		TestCase.assertEquals(geometry.getGeometry(),
				geomteryFromString.getGeometry());

	}
	
}
