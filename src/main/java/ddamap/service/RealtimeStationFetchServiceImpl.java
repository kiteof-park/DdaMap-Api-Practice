package ddamap.service;

import ddamap.client.RealtimeStationApiClient;
import ddamap.dto.NearbyStationResponse;
import ddamap.dto.RealtimeStationResponse;
import ddamap.respository.NearbyStationBase;
import ddamap.respository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RealtimeStationFetchServiceImpl {

    private final RealtimeStationApiClient apiClient;
    private final StationRepository stationRepository;

    /**
     * @return 모든 따릉이 대여소 실시간 정보
     */
    public List<RealtimeStationResponse> getAllRealtimeStations() {
        return apiClient.getAllRealtimeStations();
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @param radius
     * @param limit
     * @param concurrency
     * @return
     */public List<NearbyStationResponse> getNearbyStations(double latitude,
                                                            double longitude,
                                                            int radius,
                                                            int limit,
                                                            int concurrency){
        // 현재 좌표 반경 내 대여소 리스트
        final List<NearbyStationBase> bases = stationRepository.findNearbyStationBase(longitude, latitude, radius, limit);
        if(bases.isEmpty()){
            return List.of();
        }

        // 대여소별로 실시간 api 개별 호출
        List<NearbyStationResponse> responses = new ArrayList<>();
        for(NearbyStationBase base : bases){
            apiClient.getRealtimeStation(base.getStationId())
                    .ifPresent(responses::add);
        }
        return responses;
    }
}
