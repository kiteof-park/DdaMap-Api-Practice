package ddamap.service;

import ddamap.client.StationApiClient;
import ddamap.domain.Station;
import ddamap.dto.StationResponse;
import ddamap.respository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 대여소 정보를 db에 저장/갱신
 */
@Service
@RequiredArgsConstructor
public class StationFetchServiceImpl {

    private final StationApiClient apiClient;
    private final StationRepository repository;

    @Transactional
    public void syncStations() {
        List<StationResponse> responses = apiClient.getAllStations();
        List<Station> stations = responses.stream()
                .map(StationResponse::toEntity)
                .toList();

        repository.deleteAllInBatch();  // 스케줄러로 갱신 시 필요
        repository.saveAll(stations);
    }
}
