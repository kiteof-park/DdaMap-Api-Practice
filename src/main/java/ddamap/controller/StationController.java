package ddamap.controller;

import ddamap.service.StationFetchServiceImpl;
import ddamap.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StationController {

    private final StationFetchServiceImpl stationFetchService;

    @GetMapping("/api/station/sync")
    public void syncStations() {
        stationFetchService.syncStations();
    }
}
