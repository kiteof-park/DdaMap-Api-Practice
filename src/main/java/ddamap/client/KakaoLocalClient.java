package ddamap.client;

import ddamap.config.KakaoLocalProperties;
import ddamap.dto.KakaoAddressResponse;
import ddamap.dto.Coordinate;
import ddamap.dto.KakaoKeywordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 도로명 주소, 지번 주소, 키워드(건물이름 등) 검색 시 위도,경도로 변환하는 카카오 로컬 api 호출
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoLocalClient {

    private final WebClient.Builder webClient;
    private final KakaoLocalProperties properties;

    // 매 요청마다 build()❌, 한 번만 빌드에서 재사용
    private WebClient kakaoClient(){
        return webClient
                .baseUrl(properties.getBaseUrl())
                .defaultHeader("Authorization", "KakaoAK " + properties.getKey())
                .build();
    }

    public Optional<Coordinate> getCoordinateByAddress(String address) {
        WebClient client = kakaoClient();
        return client.get()
                .uri(uri -> uri
                        .path("/address.json")
                        .queryParam("query", address.trim())
                        .build())
                .retrieve()
                .bodyToMono(KakaoAddressResponse.class)
                .blockOptional()
                .flatMap(response -> response.getFirstCoordinate());
    }

    // TODO: 키워드로 주소 검색("맥도날드 강남" -> 주소 및 위경도)
    public Optional<Coordinate> getCoordinateByKeyword(String keyword) {
        WebClient client = kakaoClient();

        return client.get()
                .uri(uri -> uri
                        .path("/keyword.json")
                        .queryParam("query", keyword.trim())
                        .build())
                .retrieve()
                .bodyToMono(KakaoKeywordResponse.class)
                .blockOptional()
                .flatMap(response -> response.getFirstCoordinate());

//        KakaoKeywordResponse response = client.get()
//                .uri(uri -> uri
//                        .path("/keyword.json")
//                        .queryParam("query", keyword)
//                        .build())
//                .header("Authorization", "KakaoAK " + properties.getKey())
//                .retrieve()
//                .bodyToMono(KakaoKeywordResponse.class)
//                .doOnNext(res -> log.info("▶ 카카오 응답 전체: {}", res))
//                .block(); // blockOptional() 대신 block()
//
//        return Optional.ofNullable(response)
//                .flatMap(KakaoKeywordResponse::getFirstCoordinate);

    }
}
