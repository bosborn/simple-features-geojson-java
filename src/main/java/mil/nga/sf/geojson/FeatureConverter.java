package mil.nga.sf.geojson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import mil.nga.sf.util.SFException;

/**
 * Feature Converter
 * 
 * @author osbornb
 */
public class FeatureConverter {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger
			.getLogger(FeatureConverter.class.getName());

	/**
	 * Object mapper
	 */
	public final static ObjectMapper mapper = new ObjectMapper().configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	/**
	 * Convert the string content to a feature
	 * 
	 * @param content
	 *            string content
	 * @return feature
	 */
	public static Feature toFeature(String content) {
		return toTypedObject(Feature.class, content);
	}

	/**
	 * Convert the object value to a feature
	 * 
	 * @param value
	 *            object value
	 * @return feature
	 */
	public static Feature toFeature(Object value) {
		return toTypedObject(Feature.class, value);
	}

	/**
	 * Convert the JSON tree to a feature
	 * 
	 * @param tree
	 *            tree node
	 * @return feature
	 */
	public static Feature toFeature(JsonNode tree) {
		return toTypedObject(Feature.class, tree);
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
		return toTypedObject(FeatureCollection.class, content);
	}

	/**
	 * Convert the object value to a feature collection
	 * 
	 * @param value
	 *            object value
	 * @return feature collection
	 */
	public static FeatureCollection toFeatureCollection(Object value) {
		return toTypedObject(FeatureCollection.class, value);
	}

	/**
	 * Convert the JSON tree to a feature collection
	 * 
	 * @param tree
	 *            tree node
	 * @return feature collection
	 */
	public static FeatureCollection toFeatureCollection(JsonNode tree) {
		return toTypedObject(FeatureCollection.class, tree);
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
		return toTypedObject(Geometry.class, content);
	}

	/**
	 * Convert the object value to a geometry
	 * 
	 * @param value
	 *            object value
	 * @return geometry
	 */
	public static Geometry toGeometry(Object value) {
		return toTypedObject(Geometry.class, value);
	}

	/**
	 * Convert the JSON tree to a Geometry
	 * 
	 * @param tree
	 *            tree node
	 * @return geometry
	 */
	public static Geometry toGeometry(JsonNode tree) {
		return toTypedObject(Geometry.class, tree);
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
		return toTypedObject(GeoJsonObject.class, content);
	}

	/**
	 * Convert the object value to a GeoJSON object
	 * 
	 * @param value
	 *            object value
	 * @return GeoJSON object
	 */
	public static GeoJsonObject toGeoJsonObject(Object value) {
		return toTypedObject(GeoJsonObject.class, value);
	}

	/**
	 * Convert the JSON tree to a GeoJSON object
	 * 
	 * @param tree
	 *            tree node
	 * @return GeoJSON object
	 */
	public static GeoJsonObject toGeoJsonObject(JsonNode tree) {
		return toTypedObject(GeoJsonObject.class, tree);
	}

	/**
	 * Convert the string content to a simple geometry
	 * 
	 * @param content
	 *            string content
	 * @return simple geometry
	 * @since 3.3.3
	 */
	public static mil.nga.sf.Geometry toSimpleGeometry(String content) {
		return toSimpleGeometry(toGeoJsonObject(content));
	}

	/**
	 * Convert the object value to a simple geometry
	 * 
	 * @param value
	 *            object value
	 * @return simple geometry
	 * @since 3.3.3
	 */
	public static mil.nga.sf.Geometry toSimpleGeometry(Object value) {
		return toSimpleGeometry(toGeoJsonObject(value));
	}

	/**
	 * Convert the JSON tree to a simple geometry
	 * 
	 * @param tree
	 *            tree node
	 * @return simple geometry
	 * @since 3.3.3
	 */
	public static mil.nga.sf.Geometry toSimpleGeometry(JsonNode tree) {
		return toSimpleGeometry(toGeoJsonObject(tree));
	}

	/**
	 * Convert the GeoJSON object to a simple geometry
	 * 
	 * @param geoJson
	 *            GeoJSON object
	 * @return simple geometry
	 * @since 3.3.3
	 */
	public static mil.nga.sf.Geometry toSimpleGeometry(GeoJsonObject geoJson) {
		mil.nga.sf.Geometry geometry = null;
		if (geoJson != null) {
			geometry = geoJson.getSimpleGeometry();
		}
		return geometry;
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
	public static Map<String, Object> toMap(
			mil.nga.sf.Geometry simpleGeometry) {
		Geometry geometry = toGeometry(simpleGeometry);
		Map<String, Object> map = toMap(geometry);
		return map;
	}

	/**
	 * Convert the object to a string value
	 * 
	 * @param object
	 *            object
	 * @return string value
	 */
	public static String toStringValue(Object object) {
		String stringValue = null;
		try {
			stringValue = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new SFException(
					"Failed to write object as a String: " + object, e);
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
	 * Convert the string content to a typed object
	 * 
	 * @param <T>
	 *            object class type
	 * @param type
	 *            object type
	 * @param content
	 *            string content
	 * @return typed object
	 */
	public static <T> T toTypedObject(Class<T> type, String content) {
		JsonNode tree;
		try {
			tree = mapper.readTree(content);
		} catch (Exception e) {
			throw new SFException(
					"Failed to convert content to a node tree: " + content, e);
		}
		T typedObject = toTypedObject(type, tree);
		return typedObject;
	}

	/**
	 * Convert the object value to a typed object
	 * 
	 * @param <T>
	 *            object class type
	 * @param type
	 *            object type
	 * @param value
	 *            object value
	 * @return typed object
	 */
	public static <T> T toTypedObject(Class<T> type, Object value) {
		JsonNode tree = mapper.valueToTree(value);
		T typedObject = toTypedObject(type, tree);
		return typedObject;
	}

	/**
	 * Convert the JSON tree to a typed object
	 * 
	 * @param <T>
	 *            object class type
	 * @param type
	 *            object type
	 * @param tree
	 *            tree node
	 * @return typed object
	 */
	public static <T> T toTypedObject(Class<T> type, JsonNode tree) {
		return toTypedObject(type, tree, true);
	}

	/**
	 * Convert the JSON tree to a typed object
	 * 
	 * @param <T>
	 *            object class type
	 * @param type
	 *            object type
	 * @param tree
	 *            tree node
	 * @param topLevel
	 *            true if top level node call
	 * @return typed object
	 */
	private static <T> T toTypedObject(Class<T> type, JsonNode tree,
			boolean topLevel) {

		T object;

		try {
			object = mapper.treeToValue(tree, type);
		} catch (MismatchedInputException e) {
			String originalTree = tree.toString();
			object = toTypedObject(type, tree, originalTree, e);
			if (topLevel) {
				LOGGER.log(Level.WARNING, "Failed to convert node tree to a "
						+ type.getSimpleName()
						+ " object without modifications: " + originalTree, e);
				LOGGER.log(Level.INFO,
						"Modified node tree was successfully converted to a "
								+ type.getSimpleName() + " object: " + tree);
			}
		} catch (JsonProcessingException e) {
			throw new SFException("Failed to convert node tree to a "
					+ type.getSimpleName() + " object: " + tree, e);
		}

		return object;
	}

	/**
	 * Modify the JSON tree to remove a value causing a MismatchInputException
	 * and re-attempt to create the object
	 * 
	 * @param <T>
	 *            object class type
	 * @param type
	 *            object type
	 * @param tree
	 *            tree node
	 * @param originalTree
	 *            original tree value
	 * @param mismatchedInputException
	 *            mismatch input exception
	 * @return typed object
	 */
	private static <T> T toTypedObject(Class<T> type, JsonNode tree,
			String originalTree,
			MismatchedInputException mismatchedInputException) {

		T object;

		List<Reference> path = mismatchedInputException.getPath();
		if (path != null && !path.isEmpty()) {

			try {

				ObjectNode subNode = (ObjectNode) tree;
				for (int i = 0; i < path.size(); i++) {
					String fieldName = path.get(i).getFieldName();
					if (i + 1 < path.size()) {
						subNode = (ObjectNode) tree.get(fieldName);
					} else {
						subNode.remove(fieldName);
					}
				}
				object = toTypedObject(type, tree, false);

			} catch (Exception e) {
				throw new SFException("Failed to convert node tree to a "
						+ type.getSimpleName() + " object: " + originalTree,
						mismatchedInputException);
			}

		} else {
			throw new SFException(
					"Failed to convert node tree to a " + type.getSimpleName()
							+ " object: " + originalTree,
					mismatchedInputException);
		}

		return object;
	}

}
