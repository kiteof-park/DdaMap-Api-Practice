package ddamap.dto;

import ddamap.respository.NearbyStationBase;

public record NearbyStationResponse(
        String stationId,
        String stationName,
        int parkingBikeTotCnt,
        double stationLatitude,
        double stationLongitude,
        double distance             // 현재 좌표와의 거리
) {
    public static NearbyStationResponse of(NearbyStationBase base, RealtimeStationResponse realtimeResponse) {
        return new NearbyStationResponse(
                base.getStationId(),
                base.getName(),
                Integer.parseInt(realtimeResponse.parkingBikeTotCnt()),
                base.getLatitude(),
                base.getLongitude(),
                base.getDistance()
        );
    }
}
