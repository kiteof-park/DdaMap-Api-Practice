package ddamap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ddamap.domain.Station;

public record StationResponse(
        @JsonProperty("RENT_ID") String stationId,
        @JsonProperty("STA_LOC") String locGroup,
        @JsonProperty("RENT_NO") int no,
        @JsonProperty("RENT_NM") String name,
        @JsonProperty("STA_ADD1") String address,
        @JsonProperty("STA_LAT") double latitude,
        @JsonProperty("STA_LONG") double longitude
) {
    public Station toEntity() {
        return Station.builder()
                .stationId(this.stationId)
                .locGroup(this.locGroup)
                .no(this.no)
                .name(this.name)
                .address(this.address)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }
}
