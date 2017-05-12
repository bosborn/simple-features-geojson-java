package mil.nga.sf.geojson;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import mil.nga.sf.util.PositionUtils;

public class MultiLineString  extends GeoJsonObject implements Geometry, Coordinates<List<Position>> {

	/**
	 * 
	 */
	private mil.nga.sf.MultiLineString multiLineString;

	public MultiLineString() {
	}

	public MultiLineString(List<List<Position>> positions) {
		setCoordinates(positions);
	}
	
	public MultiLineString(mil.nga.sf.MultiLineString input) {
		multiLineString = input;
	}

	/**
	 * Returns coordinates as a GeoJSON Position list
	 * @return the coordinates
	 */
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public List<List<Position>> getCoordinates() {
		List<List<Position>> result = new ArrayList<List<Position>>();
		for(mil.nga.sf.LineString lineString : multiLineString.getLineStrings()){
			List<Position> positions = new ArrayList<Position>();
			for(mil.nga.sf.Position pos : lineString.getPositions()){
				positions.add(new Position(pos));
			}
			result.add(positions);
		}
		return result;
	}

	/**
	 * Sets the coordinates from a GeoJSON Position list
	 * @param input the list
	 */
	private void setCoordinates(List<List<Position>> input) {
		List<mil.nga.sf.LineString> lineStrings = new ArrayList<mil.nga.sf.LineString>();
		for (List<Position> lineStringPositions : input) {
			List<mil.nga.sf.Position> positions = new ArrayList<mil.nga.sf.Position>();
			for (Position position: lineStringPositions){
				positions.add(position);
			}
			mil.nga.sf.LinearRing ring = new mil.nga.sf.LinearRing(PositionUtils.hasZ(positions), PositionUtils.hasM(positions));
			ring.setPositions(positions);
			lineStrings.add(ring);
		}
		if (multiLineString == null){
			multiLineString = new mil.nga.sf.MultiLineString(lineStrings);
		} else {
			multiLineString.setLineStrings(lineStrings);
		}
	}

	@Override
	public <T> T accept(GeoJsonObjectVisitor<T> geoJsonObjectVisitor) {
		return geoJsonObjectVisitor.visit(this);
	}

	@Override
	public mil.nga.sf.Geometry getGeometry() {
		return multiLineString;
	}

	@Override
	public String getType() {
		return "MultiLineString";
	}
}
