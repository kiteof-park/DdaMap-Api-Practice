package ddamap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public record AddressResponse(
        @JsonProperty("documents") List<Document> documents
) {
    public Optional<Coordinate> getFirstCoordinate() {
        if (documents != null && !documents.isEmpty()){
            Document document = documents.get(0);
            return Optional.of(new Coordinate(document.latitude, document.longitude));
        }
        return Optional.empty();
    }

    public record Document(
            @JsonProperty("address_name") String address_name,
            @JsonProperty("y") String latitude,
            @JsonProperty("x") String longitude
    ){}
}
