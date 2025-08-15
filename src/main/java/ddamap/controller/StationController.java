package ddamap.controller;

import ddamap.dto.NearbyStationResponse;
import ddamap.dto.RealtimeStationResponse;
import ddamap.service.RealtimeStationFetchServiceImpl;
import ddamap.service.StationFetchServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stations")
@Tag(name = "따릉이 대여소 API", description = "따릉이 대여소 정적/실시간 정보 조회 API")
public class StationController {

    private final StationFetchServiceImpl stationService;
    private final RealtimeStationFetchServiceImpl realtimeStationService;

    @Operation(
            summary = "따릉이 대여소 정적 정보 동기화",
            description = "하루 1회 따릉이 전체 대여소 정보를 가져와 동기화")
    @GetMapping("/sync")
    public void syncStations() {
        stationService.syncStations();
    }


    @Operation(
            summary = "실시간 따릉이 대여소 정보 조회",
            description = "전체 대여소의 실시간 상태를 조회"
    )
    @GetMapping("/realtime")
    public List<RealtimeStationResponse> getRealtimeStations() {
        return realtimeStationService.getAllRealtimeStations();
    }

    @Operation(
            summary = "현재 위치 1km반경 인근 대여소 실시간 정보 조회",
            description = "위도/경도를 기준으로 반경 내의 실시간 따릉이 대여서 정보를 반환"
    )
    @GetMapping("/nearby")
    public ResponseEntity<List<NearbyStationResponse>> getNearbyStations(
            @Parameter(description = "위도") @RequestParam double latitude,
            @Parameter(description = "경도") @RequestParam double longitude,
            @Parameter(description = "검색 반경") @RequestParam(defaultValue = "1000") int radius,
            @Parameter(description = "최대 대여소 개수") @RequestParam(defaultValue = "15") int limit,
            @Parameter(description = "동시 요청 수 제한") @RequestParam(defaultValue = "5") int concurrency){
        List<NearbyStationResponse> stations = realtimeStationService.getNearbyStations(latitude, longitude, radius, limit, concurrency);
        return ResponseEntity.ok(stations);
    }
}
