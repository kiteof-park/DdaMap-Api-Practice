package ddamap.respository;

import ddamap.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    @Query(value = """
        SELECT 
            s.station_id       AS stationId,
            s.name             AS stationName,
            s.latitude         AS latitude,
            s.longitude        AS longitude
        FROM stations s
        WHERE ST_DWithin(
                s.geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
                :radiusMeters
              )
        ORDER BY ST_Distance(
                 s.geography
                 ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
              )
        LIMIT :limit
        """, nativeQuery = true)
    List<NearbyStationBase> findNearbyStationBase(
            @Param("longitude") double longitude,
            @Param("latitude") double latitude,
            @Param("radius") int radius,
            @Param("limit") int limit
    );

}
