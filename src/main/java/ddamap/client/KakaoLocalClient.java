package ddamap.client;

import ddamap.config.KakaoLocalProperties;
import ddamap.dto.AddressResponse;
import ddamap.dto.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

/**
 * 도로명 주소, 지번 주소, 키워드(건물이름 등) 검색 시 위도,경도로 변환하는 카카오 로컬 api 호출
 */
@Component
@RequiredArgsConstructor
public class KakaoLocalClient {

    private final WebClient.Builder webClient;
    private final KakaoLocalProperties properties;

    public Optional<Coordinate> getCoordinateByAddress(String address) {
        WebClient client = webClient
                .baseUrl(properties.getBaseUrl())
                .build();

        return client.get()
                .uri(uri -> uri
                        .path("/v2/local/search/address.json")
                        .queryParam("query", address)
                        .build())
                .header("Authorization", "KakaoAK " + properties.getKey())
                .retrieve()
                .bodyToMono(AddressResponse.class)
                .blockOptional()
                .flatMap(response -> response.getFirstCoordinate());

    }

    // TODO: 키워드로 주소 검색("맥도날드 강남" -> 주소 및 위경도)
}
