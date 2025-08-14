package ddamap.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtils {

    private static final GeometryFactory geographyFactory = new GeometryFactory(new PrecisionModel(), 4326);

    // JTS/Geo에서 x=경도(lon), y=위도(lat) 순서 항상 유지
    public static Point createPoint(double longitude, double latitude){
        return geographyFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
