package ddamap.client;

import ddamap.config.RealtimeStationProperties;
import ddamap.dto.RealtimeStationFullResponse;
import ddamap.dto.RealtimeStationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RealtimeStationApiClient {

    private final WebClient.Builder webClient;
    private final RealtimeStationProperties properties;

    /**
     * http://openapi.seoul.go.kr:8088/{KEY}/{TYPE}/bikeList/{START_INDEX}/{END_INDEX}
     * @return 모든 따릉이 대여소 실시간 정보
     */
    public List<RealtimeStationResponse> getAllRealtimeStations() {
        WebClient client = webClient
                .baseUrl(properties.getBaseUrl())
                .build();

        int size = 1000;
        int start = 1;

        List<RealtimeStationResponse> realtimeStations = new ArrayList<>();

        while(true){
            final int currentStart = start;
            final int currentEnd = start + size - 1;

            RealtimeStationFullResponse fullResponse = client.get()
                    .uri(uri -> uri
                            .path("/{start}/{end}")
                            .build(properties.getKey(), currentStart, currentEnd))
                    .retrieve()
                    .bodyToMono(RealtimeStationFullResponse.class)
                    .block();

            List<RealtimeStationResponse> responses = fullResponse
                    .rentBikeStatus()
                    .row();

            realtimeStations.addAll(responses);

            if(size > responses.size()){
                break;
            }

            start += size;
        }

        return realtimeStations;
    }

    /**
     * http://openapi.seoul.go.kr:8088/{KEY}/{TYPE}/bikeList/{START_INDEX}/{END_INDEX}/{stationId}
     * @param stationId
     * @return 특정 따릉이 대여소 호출
     */
    public Optional<RealtimeStationResponse> getRealtimeStation(String stationId) {
        WebClient client = webClient
                .baseUrl(properties.getBaseUrl())
                .build();

        RealtimeStationFullResponse fullResponse =  client.get()
                .uri(uri -> uri
                        .path("/{start}/{end}/{stationId}")
                        .build(properties.getKey(), 1, 1, stationId))
                .retrieve()
                .bodyToMono(RealtimeStationFullResponse.class)
                .block();

        // TODO: 데이터가 없는 경우 ?
        if(fullResponse != null
                && fullResponse.rentBikeStatus() != null
                && fullResponse.rentBikeStatus().row() != null
                && !fullResponse.rentBikeStatus().row().isEmpty()){

            return Optional.of(fullResponse.rentBikeStatus().row().get(0));
        }

        return Optional.empty();
    }
}
