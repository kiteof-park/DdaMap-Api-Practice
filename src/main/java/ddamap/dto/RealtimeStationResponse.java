package ddamap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RealtimeStationResponse(
//        @JsonProperty("rackTotCnt")  String rackTotCnt,                 // 거치대 개수
//        @JsonProperty("shared") String shared,                          // 거치율
        @JsonProperty("parkingBikeTotCnt") String parkingBikeTotCnt,    // 자전거 주차 총 건수
        @JsonProperty("stationLatitude") double stationLatitude,        // 위도
        @JsonProperty("stationLongitude") double stationLongitude,      // 경도
        @JsonProperty("stationId") String stationId,                    // 대여소ID
        @JsonProperty("stationName") String stationName                 // 대여소 이름
) {
    // TODO: toEntity() 필요?
}
