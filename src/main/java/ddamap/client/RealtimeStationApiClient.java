package ddamap.client;

import ddamap.config.RealtimeStationProperties;
import ddamap.dto.RealtimeStationFullResponse;
import ddamap.dto.RealtimeStationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * 따릉이 실시간 api 호출 및 파싱
 */
@Component
@RequiredArgsConstructor
public class RealtimeStationApiClient {

    private final WebClient.Builder webClient;
    private final RealtimeStationProperties properties;

    public List<RealtimeStationResponse> getAllRealtimeStations() {
        WebClient client = webClient
                .baseUrl(properties.getBaseUrl())
                .build();

        int size = 1000;
        int start = 1;

        List<RealtimeStationResponse> realtimeStations = new ArrayList<>();

        while(true){
            final int cureentStart = start;
            final int cureentEnd = start + size - 1;

            RealtimeStationFullResponse fullResponse = client.get()
                    .uri(uri -> uri
                            .path("/{start}/{end}")
                            .build(properties.getKey(), cureentStart, cureentEnd))
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
}
