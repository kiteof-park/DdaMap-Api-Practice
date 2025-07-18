package ddamap.client;

import ddamap.config.StationProperties;

import ddamap.dto.StationFullResponse;
import ddamap.dto.StationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * 따릉이 api 호출 및 파싱
 */
@Component
@RequiredArgsConstructor
public class StationApiClient {

    private final WebClient.Builder webClient;
    private final StationProperties properties;

    public List<StationResponse> getAllStations() {
        WebClient client = webClient
                .baseUrl(properties.getBaseUrl())
                .build();

        int size = 1000;
        int start = 1;
        List<StationResponse> stations = new ArrayList<>();

        while(true){
            final int currentStart = start;
            final int currentEnd = start + size - 1;

            // 1. api 호출 -> StationFullResponse 타입으로 받음
            StationFullResponse fullResponse = client.get()
                    .uri(uri -> uri
                            .path("/{start}/{end}")
                            .build(properties.getKey(), currentStart, currentEnd))
                    .retrieve()
                    .bodyToMono(StationFullResponse.class)
                    .block();   // 동기 방식 처리

            // 2. StationFullResponse -> StationResponse 추출
            List<StationResponse> responses = fullResponse
                    .stationInfo()
                    .row();

            // TODO: api 응답값이 잘못된 경우(null, empty) 예외처리?

            stations.addAll(responses);

            if(responses.size() < size){
                break;
            }

            start+=size;
        }

        return stations;
    }
}
