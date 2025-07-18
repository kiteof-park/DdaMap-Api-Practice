package ddamap.scheduler;

import ddamap.client.StationApiClient;
import ddamap.service.StationFetchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StationSyncScheduler {

    private final StationFetchServiceImpl fetchService;

    @Scheduled(cron = "0 0 3 * * *")
    public void syncStations() {
        fetchService.syncStations();
    }
}
