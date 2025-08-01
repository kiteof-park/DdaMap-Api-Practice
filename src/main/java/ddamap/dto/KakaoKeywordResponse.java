package ddamap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public record KakaoKeywordResponse(
        @JsonProperty("documents") List<KeywordDocument> documents

) {
    public Optional<Coordinate> getFirstCoordinate() {
        if(documents != null && !documents.isEmpty()) {
            KeywordDocument document = documents.get(0);
            return Optional.of(new Coordinate(document.latitude, document.longitude));
        }
        return Optional.empty();
    }

    public record KeywordDocument(
            @JsonProperty("place_name") String placeName,
            @JsonProperty("address_name") String addressName,
            @JsonProperty("road_address_name") String roadAddressName,
            @JsonProperty("y") String latitude,
            @JsonProperty("x") String longitude
    ){}
}
