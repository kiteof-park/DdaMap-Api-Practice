package ddamap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RealtimeStationFullResponse(
        @JsonProperty("rentBikeStatus") RentBikeStatus rentBikeStatus
) {
    public record RentBikeStatus(
            @JsonProperty("row") List<RealtimeStationResponse> row
    ){}
}
