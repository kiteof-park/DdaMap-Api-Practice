package ddamap.controller;

import ddamap.dto.RealtimeStationResponse;
import ddamap.service.RealtimeStatitonFetchServiceImpl;
import ddamap.service.StationFetchServiceImpl;
import ddamap.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stations")
public class StationController {

    private final StationFetchServiceImpl stationService;
    private final RealtimeStatitonFetchServiceImpl realtimeStatitonService;

    @GetMapping("/sync")
    public void syncStations() {
        stationService.syncStations();
    }

    @GetMapping("/realtime")
    public List<RealtimeStationResponse> getRealtimeStations() {
        return realtimeStatitonService.getAllRealtimeStations();
    }
}
