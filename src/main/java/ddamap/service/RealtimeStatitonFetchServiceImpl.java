package ddamap.service;

import ddamap.client.RealtimeStationApiClient;
import ddamap.dto.RealtimeStationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RealtimeStatitonFetchServiceImpl {

    private final RealtimeStationApiClient apiClient;

    // 서비스 레이어 스켈레톤
    public List<RealtimeStationResponse> getAllRealtimeStations() {
        return apiClient.getAllRealtimeStations();
    }
}
