package ddamap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StationFullResponse(
        @JsonProperty("stationInfo") StationInfo stationInfo
) {
    public record StationInfo(
            @JsonProperty("list_total_count") int listTotalCount,
            @JsonProperty("row") List<StationResponse> row
    ) {}
}