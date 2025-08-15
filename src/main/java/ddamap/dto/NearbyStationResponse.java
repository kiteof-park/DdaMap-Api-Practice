package ddamap.dto;

public record NearbyStationResponse(
        String stationId,
        String stationName,
        int parkingBikeTotCnt,
        double stationLatitude,
        double stationLongitude
) {
}
