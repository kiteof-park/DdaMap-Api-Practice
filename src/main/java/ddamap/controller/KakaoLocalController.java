package ddamap.controller;

import ddamap.client.KakaoLocalClient;
import ddamap.dto.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoLocalController {

    private final KakaoLocalClient client;

    @GetMapping("/coordinate")
    public ResponseEntity<?> getCoordinate(@RequestParam String address) {
        return client.getCoordinateByAddress(address)
                .map(coordinate -> ResponseEntity.ok().body(coordinate))
                .orElse(ResponseEntity.notFound().build());
    }
}
