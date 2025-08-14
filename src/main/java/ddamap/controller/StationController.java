package ddamap.controller;

import ddamap.dto.RealtimeStationResponse;
import ddamap.dto.StationResponse;
import ddamap.service.RealtimeStatitonFetchServiceImpl;
import ddamap.service.StationFetchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stations")
public class StationController {

    private final StationFetchServiceImpl stationService;
    private final RealtimeStatitonFetchServiceImpl realtimeStatitonService;

    /**
     * 따릉이 대여소 정적 정보 호출 -> 스케줄링
     */
    @GetMapping("/sync")
    public void syncStations() {
        stationService.syncStations();
    }

    /**
     * 따릉이 전체 대여소 실시간 정보 호출
     * @return
     */
    @GetMapping("/realtime")
    public List<RealtimeStationResponse> getRealtimeStations() {
        return realtimeStatitonService.getAllRealtimeStations();
    }

    /**
     * 현재 위치 반경 따릉이 대여소 실시간 정보 호출
     * @param latitude 위도
     * @param longitude 경도
     * @param radius 반경
     * @param limit 인근 대여소 개수 제한
     * @param concurrency 동시 요청 호출 수 제한
     * @return 현재 위치 반경 1km 이내 따릉이 대여소 실시간 정보
     */
    @GetMapping("/nearby")
    public List<RealtimeStationResponse> getNearbyStatitons(
                                            @RequestParam double latitude,
                                            @RequestParam double longitude,
                                            @RequestParam(defaultValue = "1000") int radius,
                                            @RequestParam(defaultValue = "15") int limit,
                                            @RequestParam(defaultValue = "5") int concurrency){
        return realtimeStatitonService.getNearbyStations(latitude, longitude, radius, limit, concurrency);
    }
}
