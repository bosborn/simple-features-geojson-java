package mil.nga.sf.geojson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mil.nga.sf.util.SFException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Feature Converter
 * 
 * @author osbornb
 */
public class FeatureConverter {

	/**
	 * Object mapper
	 */
	public final static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Convert the string content to a feature
	 * 
	 * @param content
	 *            string content
	 * @return feature
	 */
	public static Feature toFeature(String content) {
		return toTypedGeoJsonObject(Feature.class, content);
	}

	/**
	 * Convert the object value to a feature
	 * 
	 * @param value
	 *            object value
	 * @return feature
	 */
	public static Feature toFeature(Object value) {
		return toTypedGeoJsonObject(Feature.class, value);
	}

	/**
	 * Convert the JSON tree to a feature
	 * 
	 * @param tree
	 *            tree node
	 * @return feature
	 */
	public static Feature toFeature(JsonNode tree) {
		return toTypedGeoJsonObject(Feature.class, tree);
	}

	/**
	 * Convert a simple geometry to a feature
	 * 
	 * @param simpleGeometry
	 *            simple geometry
	 * @return feature
	 */
	public static Feature toFeature(mil.nga.sf.Geometry simpleGeometry) {
		Geometry geometry = toGeometry(simpleGeometry);
		Feature feature = new Feature(geometry);
		return feature;
	}

	/**
	 * Convert the string content to a feature collection
	 * 
	 * @param content
	 *            string content
	 * @return feature collection
	 */
	public static FeatureCollection toFeatureCollection(String content) {
		return toTypedGeoJsonObject(FeatureCollection.class, content);
	}

	/**
	 * Convert the object value to a feature collection
	 * 
	 * @param value
	 *            object value
	 * @return feature collection
	 */
	public static FeatureCollection toFeatureCollection(Object value) {
		return toTypedGeoJsonObject(FeatureCollection.class, value);
	}

	/**
	 * Convert the JSON tree to a feature collection
	 * 
	 * @param tree
	 *            tree node
	 * @return feature collection
	 */
	public static FeatureCollection toFeatureCollection(JsonNode tree) {
		return toTypedGeoJsonObject(FeatureCollection.class, tree);
	}

	/**
	 * Convert a simple geometry to a feature collection
	 * 
	 * @param simpleGeometry
	 *            simple geometry
	 * @return feature collection
	 */
	public static FeatureCollection toFeatureCollection(
			mil.nga.sf.Geometry simpleGeometry) {
		Feature feature = toFeature(simpleGeometry);
		FeatureCollection featureCollection = new FeatureCollection(feature);
		return featureCollection;
	}

	/**
	 * Convert simple geometries to a feature collection
	 * 
	 * @param simpleGeometries
	 *            simple geometries
	 * @return feature collection
	 */
	public static FeatureCollection toFeatureCollection(
			Collection<mil.nga.sf.Geometry> simpleGeometries) {
		List<Feature> features = new ArrayList<>();
		for (mil.nga.sf.Geometry simpleGeometry : simpleGeometries) {
			Feature feature = toFeature(simpleGeometry);
			features.add(feature);
		}
		FeatureCollection featureCollection = new FeatureCollection(features);
		return featureCollection;
	}

	/**
	 * Convert the string content to a geometry
	 * 
	 * @param content
	 *            string content
	 * @return geometry
	 */
	public static Geometry toGeometry(String content) {
		return toTypedGeoJsonObject(Geometry.class, content);
	}

	/**
	 * Convert the object value to a geometry
	 * 
	 * @param value
	 *            object value
	 * @return geometry
	 */
	public static Geometry toGeometry(Object value) {
		return toTypedGeoJsonObject(Geometry.class, value);
	}

	/**
	 * Convert the JSON tree to a Geometry
	 * 
	 * @param tree
	 *            tree node
	 * @return geometry
	 */
	public static Geometry toGeometry(JsonNode tree) {
		return toTypedGeoJsonObject(Geometry.class, tree);
	}

	/**
	 * Convert a simple geometry to a GeoJSON geometry
	 * 
	 * @param simpleGeometry
	 *            simple geometry
	 * @return geometry
	 */
	public static Geometry toGeometry(mil.nga.sf.Geometry simpleGeometry) {
		Geometry geometry = null;
		if (simpleGeometry != null) {
			if (simpleGeometry instanceof mil.nga.sf.Point) {
				geometry = new Point((mil.nga.sf.Point) simpleGeometry);
			} else if (simpleGeometry instanceof mil.nga.sf.LineString) {
				geometry = new LineString(
						(mil.nga.sf.LineString) simpleGeometry);
			} else if (simpleGeometry instanceof mil.nga.sf.MultiPoint) {
				geometry = new MultiPoint(
						(mil.nga.sf.MultiPoint) simpleGeometry);
			} else if (simpleGeometry instanceof mil.nga.sf.Polygon) {
				geometry = new Polygon((mil.nga.sf.Polygon) simpleGeometry);
			} else if (simpleGeometry instanceof mil.nga.sf.MultiLineString) {
				geometry = new MultiLineString(
						(mil.nga.sf.MultiLineString) simpleGeometry);
			} else if (simpleGeometry instanceof mil.nga.sf.MultiPolygon) {
				geometry = new MultiPolygon(
						(mil.nga.sf.MultiPolygon) simpleGeometry);
			} else if (simpleGeometry instanceof mil.nga.sf.GeometryCollection<?>) {
				@SuppressWarnings("unchecked")
				mil.nga.sf.GeometryCollection<mil.nga.sf.Geometry> simpleGeometryCollection = (mil.nga.sf.GeometryCollection<mil.nga.sf.Geometry>) simpleGeometry;
				geometry = new GeometryCollection(simpleGeometryCollection);
			} else {
				throw new SFException("Unsupported Geometry type: "
						+ simpleGeometry.getClass().getSimpleName());
			}
		}
		return geometry;
	}

	/**
	 * Convert the string content to a GeoJSON object
	 * 
	 * @param content
	 *            string content
	 * @return GeoJSON object
	 */
	public static GeoJsonObject toGeoJsonObject(String content) {
		return toTypedGeoJsonObject(GeoJsonObject.class, content);
	}

	/**
	 * Convert the object value to a GeoJSON object
	 * 
	 * @param value
	 *            object value
	 * @return GeoJSON object
	 */
	public static GeoJsonObject toGeoJsonObject(Object value) {
		return toTypedGeoJsonObject(GeoJsonObject.class, value);
	}

	/**
	 * Convert the JSON tree to a GeoJSON object
	 * 
	 * @param tree
	 *            tree node
	 * @return GeoJSON object
	 */
	public static GeoJsonObject toGeoJsonObject(JsonNode tree) {
		return toTypedGeoJsonObject(GeoJsonObject.class, tree);
	}

	/**
	 * Convert the GeoJSON object to an object map
	 * 
	 * @param object
	 *            GeoJSON object
	 * @return object map
	 */
	public static Map<String, Object> toMap(GeoJsonObject object) {
		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
		};
		Map<String, Object> map = mapper.convertValue(object, typeRef);
		return map;
	}

	/**
	 * Convert the simple geometry to an object map
	 * 
	 * @param simpleGeometry
	 *            simple geometry
	 * @return object map
	 */
	public static Map<String, Object> toMap(mil.nga.sf.Geometry simpleGeometry) {
		Geometry geometry = toGeometry(simpleGeometry);
		Map<String, Object> map = toMap(geometry);
		return map;
	}

	/**
	 * Convert the GeoJSON object to a string value
	 * 
	 * @param object
	 *            GeoJSON object
	 * @return string value
	 */
	public static String toStringValue(GeoJsonObject object) {
		String stringValue = null;
		try {
			stringValue = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new SFException(
					"Failed to write GeoJSON object as a String: "
							+ object.getType(), e);
		}
		return stringValue;
	}

	/**
	 * Convert the simple geometry to a string value
	 * 
	 * @param simpleGeometry
	 *            simple geometry
	 * @return string value
	 */
	public static String toStringValue(mil.nga.sf.Geometry simpleGeometry) {
		Geometry geometry = toGeometry(simpleGeometry);
		String stringValue = toStringValue(geometry);
		return stringValue;
	}

	/**
	 * Convert the string content to a typed GeoJSON object
	 * 
	 * @param type
	 *            GeoJSON object type
	 * @param content
	 *            string content
	 * @return typed GeoJSON object
	 */
	private static <T extends GeoJsonObject> T toTypedGeoJsonObject(
			Class<T> type, String content) {
		JsonNode tree;
		try {
			tree = mapper.readTree(content);
		} catch (Exception e) {
			throw new SFException("Failed to convert content to a node tree: "
					+ content, e);
		}
		T typedGeoJsonObject = toTypedGeoJsonObject(type, tree);
		return typedGeoJsonObject;
	}

	/**
	 * Convert the object value to a typed GeoJSON object
	 * 
	 * @param type
	 *            GeoJSON object type
	 * @param value
	 *            object value
	 * @return typed GeoJSON object
	 */
	private static <T extends GeoJsonObject> T toTypedGeoJsonObject(
			Class<T> type, Object value) {
		JsonNode tree = mapper.valueToTree(value);
		T typedGeoJsonObject = toTypedGeoJsonObject(type, tree);
		return typedGeoJsonObject;
	}

	/**
	 * Convert the JSON tree to a typed GeoJSON object
	 * 
	 * @param type
	 *            GeoJSON object type
	 * @param tree
	 *            tree node
	 * @return typed GeoJSON object
	 */
	private static <T extends GeoJsonObject> T toTypedGeoJsonObject(
			Class<T> type, JsonNode tree) {
		T geoJsonObject;
		try {
			geoJsonObject = mapper.treeToValue(tree, type);
		} catch (JsonProcessingException e) {
			throw new SFException(
					"Failed to convert node tree to GeoJSON object: " + tree, e);
		}
		return geoJsonObject;
	}

}