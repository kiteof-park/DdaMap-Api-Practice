package ddamap.service;

import ddamap.client.RealtimeStationApiClient;
import ddamap.dto.NearbyStationResponse;
import ddamap.dto.RealtimeStationResponse;
import ddamap.respository.NearbyStationBase;
import ddamap.respository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * 현재 좌표 인근 대여소 실시간 정보
     * @param latitude      위도     
     * @param longitude     경도
     * @param radius        반경
     * @param limit         대여소 수 제한
     * @param concurrency   병렬 호출 수 제한
     * @return  인근 대여소 실시간 정보 리스트
     */
    public List<NearbyStationResponse> getNearbyStations(double latitude,
                                                            double longitude,
                                                            int radius,
                                                            int limit,
                                                            int concurrency){
        // 현재 좌표 반경 내 대여소 리스트 조회
        final List<NearbyStationBase> bases = stationRepository.findNearbyStationBase(longitude, latitude, radius, limit);
        if(bases.isEmpty()){
            return List.of();
        }

        // stationsId 리스트 추출
        List<String> stationIds = bases.stream()
                .map(NearbyStationBase::getStationId)
                .toList();

        // 따릉이 실시간 정보 호출(stationId로 호출) -> 병렬 호출, 동시성 제한
        List<RealtimeStationResponse> realtimeResponses = Flux.fromIterable(stationIds)
                .flatMap(stationId ->
                        Mono.fromCallable(() -> apiClient.getRealtimeStation(stationId).orElse(null)),
                        concurrency
                )
                .filter(Objects::nonNull)
                .collectList()
                .block();

        // 데이터 매핑(key = stationId, value = RealtimeStationResponse)
        Map<String, RealtimeStationResponse> realtimeMap = realtimeResponses.stream()
                .collect(Collectors.toMap(RealtimeStationResponse::stationId, r -> r));

        // 프로젝션 인터페이스 + realtimeMap
        return bases.stream()
                .map(s -> NearbyStationResponse.of(s, realtimeMap.get(s.getStationId())))
                .toList();
    }
}
