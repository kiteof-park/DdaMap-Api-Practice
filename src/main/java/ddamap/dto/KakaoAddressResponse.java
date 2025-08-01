package ddamap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public record KakaoAddressResponse(
        @JsonProperty("documents") List<AddressDocument> documents
) {
    public Optional<Coordinate> getFirstCoordinate() {
        if (documents != null && !documents.isEmpty()){
            AddressDocument document = documents.get(0);
            return Optional.of(new Coordinate(document.latitude, document.longitude));
        }
        return Optional.empty();
    }

    public record AddressDocument(
//            @JsonProperty("address_name") String addressName,
            @JsonProperty("y") String latitude,
            @JsonProperty("x") String longitude
    ){}
}
